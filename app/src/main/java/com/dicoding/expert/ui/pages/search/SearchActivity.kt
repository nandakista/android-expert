package com.dicoding.expert.ui.pages.search

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.expert.R
import com.dicoding.expert.core.AppConst
import com.dicoding.expert.core.Tools
import com.dicoding.expert.data.models.base.ApiResult
import com.dicoding.expert.databinding.ActivitySearchBinding
import com.dicoding.expert.ui.adapters.UsersAdapter
import com.dicoding.expert.ui.pages.favorite.FavoriteActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    var querySearch: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbarHome)
        onSwipeRefresh()
        if(savedInstanceState != null) {
            querySearch = savedInstanceState.getString(QUERY_SEARCH)
        }
    }

    override fun onResume() {
        super.onResume()
        querySearch?.let { searchUser(it) }
    }

    private fun searchUser(query: String) {
        val factory: SearchViewModelFactory = SearchViewModelFactory.getInstance()
        val viewModel: SearchViewModel by viewModels { factory }
        val adapter = UsersAdapter()
        binding.rvUsers.adapter = adapter
        viewModel.searchUser(query).observe(this) {
            when (it) {
                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.userEmpty.visibility = View.GONE
                    binding.userNotFound.visibility = View.GONE
                    binding.rvUsers.visibility = View.GONE
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.userEmpty.visibility = View.GONE
                    binding.userNotFound.visibility = View.GONE
                    adapter.submitList(it.data)
                    binding.rvUsers.apply {
                        visibility = View.VISIBLE
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@SearchActivity)
                    }
                }
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.userEmpty.visibility = View.GONE
                    binding.rvUsers.visibility = View.GONE
                    binding.userNotFound.visibility = View.VISIBLE
                    if (it.error == AppConst.userNotFound) {
                        Tools.toast(this, it.error)
                    } else {
                        Tools.toast(this, "Error : ${it.error}")
                    }
                }
            }
        }
    }

    private fun onSwipeRefresh() {
        binding.srHome.setOnRefreshListener {
            querySearch?.let { searchUser(it) }
            Handler(Looper.getMainLooper()).postDelayed({ binding.srHome.isRefreshing = false }, 500)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_icon).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                querySearch = query
                searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
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
                startActivity(Intent(this@SearchActivity, FavoriteActivity::class.java))
                true
            }
            else -> true
        }
    }

    companion object {
        private const val QUERY_SEARCH = "query_search"
    }
}