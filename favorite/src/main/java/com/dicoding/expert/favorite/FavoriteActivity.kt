package com.dicoding.expert.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.expert.di.FavoriteModuleDependencies
import com.dicoding.expert.favorite.databinding.ActivityFavoriteBinding
import com.dicoding.expert.ui.adapter.UsersAdapter
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbarFav)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val adapter = UsersAdapter()
        binding.rvFavUsers.adapter = adapter
        viewModel.getFavUser.observe(this) {
            Log.d("Activity", "Fav = ${it.map { e ->
                e.username
            }}")
            if (it.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.rvFavUsers.apply {
                    visibility = View.VISIBLE
                    binding.favUserEmpty.visibility = View.INVISIBLE
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    adapter.submitList(it)
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