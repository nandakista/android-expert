package com.dicoding.expert.ui.pages.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.expert.R
import com.dicoding.expert.core.utils.AppConst
import com.dicoding.expert.core.utils.Tools
import com.dicoding.expert.core.utils.ViewModelFactory
import com.dicoding.expert.data.sources.Resource
import com.dicoding.expert.databinding.ActivityHomeBinding
import com.dicoding.expert.ui.adapters.UsersAdapter
import com.dicoding.expert.ui.pages.favorite.FavoriteActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import java.lang.StringBuilder

@ExperimentalCoroutinesApi
@FlowPreview
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    var querySearch: String? = null

    private lateinit var factory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbarHome)
        factory = ViewModelFactory.getInstance(this@HomeActivity)
        onSwipeRefresh()
        if (savedInstanceState != null) {
            querySearch = savedInstanceState.getString(QUERY_SEARCH)
        }
        getAllUser()
    }

    override fun onResume() {
        super.onResume()
        querySearch?.let { searchUser() }
    }

    private fun getAllUser() {
        val adapter = UsersAdapter()
        binding.rvUsers.adapter = adapter
        viewModel.getAllUser.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.userEmpty.visibility = View.GONE
                    binding.userError.visibility = View.GONE
                    binding.rvUsers.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.userEmpty.visibility = View.GONE
                    binding.userError.visibility = View.GONE
                    adapter.submitList(it.data)
                    binding.rvUsers.apply {
                        visibility = View.VISIBLE
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@HomeActivity)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.userEmpty.visibility = View.GONE
                    binding.rvUsers.visibility = View.GONE
                    binding.userError.visibility = View.VISIBLE
                    if (it.message == AppConst.userNotFound) {
                        binding.tvErrorMsg.text = StringBuilder().append(AppConst.userNotFound)
                        Tools.toast(this, it.message)
                    } else {
                        binding.tvErrorMsg.text = StringBuilder().append(it.message)
                        Tools.toast(this, "Error : ${it.message}")
                    }
                }
            }
        }
    }

    private fun searchUser() {
        val adapter = UsersAdapter()
        binding.rvUsers.adapter = adapter
        viewModel.searchResult.observe(this) { data ->
            data.observe(this) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.userEmpty.visibility = View.GONE
                        binding.userError.visibility = View.GONE
                        binding.rvUsers.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.userEmpty.visibility = View.GONE
                        binding.userError.visibility = View.GONE
                        adapter.submitList(it.data)
                        binding.rvUsers.apply {
                            visibility = View.VISIBLE
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(this@HomeActivity)
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.userEmpty.visibility = View.GONE
                        binding.rvUsers.visibility = View.GONE
                        binding.userError.visibility = View.VISIBLE
                        if (it.message == AppConst.userNotFound) {
                            binding.tvErrorMsg.text = StringBuilder().append(AppConst.userNotFound)
                            Tools.toast(this, it.message)
                        } else {
                            binding.tvErrorMsg.text = StringBuilder().append(it.message)
                            Tools.toast(this, "Error : ${it.message}")
                        }
                    }
                }
            }
        }
    }

    private fun onSwipeRefresh() {
        binding.srHome.setOnRefreshListener {
            querySearch?.let {
                lifecycleScope.launch {
                    viewModel.queryChannel.value = it
                    searchUser()
                }
            }
            Handler(Looper.getMainLooper()).postDelayed({
                    binding.srHome.isRefreshing = false
                }, 500
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_icon).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setQuery(querySearch, true)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                querySearch = query
                searchUser()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                querySearch = query
                lifecycleScope.launch {
                    viewModel.queryChannel.value = query
                    searchUser()
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_SEARCH, querySearch)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_icon -> {
                startActivity(Intent(this@HomeActivity, FavoriteActivity::class.java))
                true
            }
            else -> true
        }
    }

    companion object {
        private const val QUERY_SEARCH = "query_search"
    }
}