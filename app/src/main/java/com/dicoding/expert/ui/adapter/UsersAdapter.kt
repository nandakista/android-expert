package com.dicoding.expert.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.core.domain.model.User
import com.dicoding.expert.R
import com.dicoding.expert.databinding.ItemUserBinding
import com.dicoding.expert.ui.pages.detail.DetailUserActivity

class UsersAdapter : ListAdapter<User, UsersAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    inner class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            binding.tvUsername.text = data.username
            val gitUrls = data.gitUrl
            binding.tvGitUrl.text = gitUrls?.let { gitUrls.substring(8, it.length) }
            binding.tvTypeUser.text = data.userType
            Glide.with(itemView.context)
                .load(data.avatarUrl)
                .apply(
                    RequestOptions
                        .placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.ivAvatar)

            itemView.setOnClickListener {
                val context = it.context
                context.startActivity(
                    Intent(context, DetailUserActivity::class.java)
                        .putExtra(DetailUserActivity.EXTRA_USER, data)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}