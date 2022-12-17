package com.dicoding.expert.ui.pages.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.expert.data.models.User
import com.dicoding.expert.databinding.ActivityFavoriteBinding
import com.dicoding.expert.ui.adapters.UsersAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbarFav)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val viewModel: FavoriteViewModel by viewModels { factory }
        val adapter = UsersAdapter()
        binding.rvFavUsers.adapter = adapter
        viewModel.getFavUser.observe(this) { listUserEntity ->
            if (listUserEntity.isNotEmpty()) {
                val userList = listUserEntity.map { userEntity ->
                    User(
                        userId = userEntity.id,
                        username = userEntity.username.toString(),
                        name = userEntity.name,
                        gitUrl = userEntity.gitUrl,
                        location = userEntity.location,
                        company = userEntity.company,
                        avatarUrl = userEntity.avatarUrl,
                        userType = userEntity.userType,
                    )
                }
                binding.progressBar.visibility = View.GONE
                binding.rvFavUsers.apply {
                    visibility = View.VISIBLE
                    binding.favUserEmpty.visibility = View.INVISIBLE
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    adapter.submitList(userList)
                }
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvFavUsers.visibility = View.GONE
                binding.favUserEmpty.visibility = View.VISIBLE
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}