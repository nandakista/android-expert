package com.dicoding.expert.ui.pages.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.expert.R
import com.dicoding.expert.core.Tools
import com.dicoding.expert.core.database.entity.UserEntity
import com.dicoding.expert.core.parcelable
import com.dicoding.expert.data.models.User
import com.dicoding.expert.data.models.base.ApiResult
import com.dicoding.expert.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var factory: DetailViewModelFactory
    private val viewModel: DetailUserViewModel by viewModels { factory }
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.parcelable(EXTRA_USER)!!
        binding.tvTitle.text = user.username
        supportActionBar?.elevation = 0f
        factory = DetailViewModelFactory.getInstance(this@DetailUserActivity)
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
        viewModel.getDetailUser(user.username).observe(this) {
            when (it) {
                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.somethingWrong.visibility = View.VISIBLE
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    user = it.data
                    setProfile(user)
                }
            }
        }
    }

    private fun checkUserExist() {
        CoroutineScope(Dispatchers.IO).launch {
            if (viewModel.hasAdded(user.userId!!)) {
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
                viewModel.addFavUser(
                    user = UserEntity(
                        id = user.userId,
                        username = user.username,
                        name = user.name,
                        gitUrl = user.gitUrl,
                        location = user.location,
                        company = user.company,
                        avatarUrl = user.avatarUrl,
                        userType = user.userType
                    )
                )
                binding.menuAddFav.visibility = View.GONE
                binding.menuRemoveFav.visibility = View.VISIBLE
                Tools.toast(
                    this@DetailUserActivity,
                    "${user.username} has been added to Favorite Developer."
                )
            }
            binding.menuRemoveFav -> {
                viewModel.deletedFavUser(user.userId!!)
                binding.menuRemoveFav.visibility = View.GONE
                binding.menuAddFav.visibility = View.VISIBLE
                Tools.toast(this, "${user.username} has been remove from Favorite Developer.")
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}