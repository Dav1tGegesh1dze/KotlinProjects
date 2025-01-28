package com.example.task20

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task20.Client.User
import com.example.task20.databinding.ListItemBinding

class UserListAdapter : PagingDataAdapter<User, UserListAdapter.UserViewHolder>(UserDiffCallback()) {

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }

    class UserViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.apply {
                emailTextView.text = user.email
                firstNameTextView.text = user.firstName
                lastNameTextView.text = user.lastName
                Glide.with(root.context)
                    .load(user.avatar)
                    .into(AvatarImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }
}
