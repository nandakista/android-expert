
package com.dicoding.expert.ui.pages.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.data.Resource
import com.dicoding.expert.R
import com.dicoding.expert.ui.adapter.UsersAdapter
import com.dicoding.core.utils.AppConst
import com.dicoding.core.utils.Tools
import com.dicoding.expert.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbarHome)
        getAllUser()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_icons -> {
                val uri = Uri.parse("expert://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> true
        }
    }
}