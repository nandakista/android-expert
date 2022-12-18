package com.dicoding.expert.ui.pages.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.expert.R
import com.dicoding.expert.core.utils.Tools
import com.dicoding.expert.core.utils.ViewModelFactory
import com.dicoding.expert.core.utils.parcelable
import com.dicoding.expert.data.sources.Resource
import com.dicoding.expert.databinding.ActivityDetailUserBinding
import com.dicoding.expert.domain.entities.User

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var factory: ViewModelFactory
    private val viewModel: DetailUserViewModel by viewModels { factory }
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.parcelable(EXTRA_USER)!!
        binding.tvTitle.text = user.username
        supportActionBar?.elevation = 0f
        factory = ViewModelFactory.getInstance(this@DetailUserActivity)
        binding.menuAddFav.setOnClickListener(this)
        binding.menuRemoveFav.setOnClickListener(this)
        binding.btnBackDetail.setOnClickListener(this)
        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        return true
    }

    private fun init() {
        getDetailUser()
        checkUserExist()
    }

    private fun getDetailUser() {
        Log.d("Detail Activity", "User ${user.username}")
        viewModel.getDetailUser(user.username.toString()).observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.somethingWrong.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    user = it.data!!
                    setProfile(user)
                }
            }
        }
    }

    private fun checkUserExist() {
        viewModel.hasAdded(user.id!!).observe(this) {
            if (it) {
                binding.menuAddFav.visibility = View.GONE
                binding.menuRemoveFav.visibility = View.VISIBLE
            } else {
                binding.menuAddFav.visibility = View.VISIBLE
                binding.menuRemoveFav.visibility = View.GONE
            }
        }
    }

    private fun setProfile(data: User) {
        binding.tvFullname.text = data.name ?: "Unnamed Account"
        binding.tvBio.text = data.biodata ?: "--"
        binding.tvRepo.text = data.repository.toString()
        binding.tvFolllowers.text = data.followers.toString()
        binding.tvFollowing.text = data.following.toString()
        binding.tvCompany.text = data.company ?: "--"
        binding.tvLocation.text = data.location ?: "--"
        Glide.with(this)
            .load(data.avatarUrl)
            .apply(
                RequestOptions
                    .placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.userAvatar)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnBackDetail -> {
                finish()
            }
            binding.menuAddFav -> {
                viewModel.setFavorite(user).observe(this) {
                    binding.menuAddFav.visibility = View.GONE
                    binding.menuRemoveFav.visibility = View.VISIBLE
                    Tools.toast(
                        this@DetailUserActivity,
                        "${user.username} has been added to Favorite."
                    )
                }
            }
            binding.menuRemoveFav -> {
                viewModel.deletedFavUser(user.id!!).observe(this) {
                    binding.menuRemoveFav.visibility = View.GONE
                    binding.menuAddFav.visibility = View.VISIBLE
                    Tools.toast(
                        this@DetailUserActivity,
                        "${user.username} has been remove from Favorite."
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}