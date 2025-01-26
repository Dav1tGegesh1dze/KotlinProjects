package com.example.task18.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task18.Clients.UserProfile
import com.example.task18.databinding.ListItemBinding

class UsersListAdapter : ListAdapter<UserProfile, UsersListAdapter.UserViewHolder>(UserDiffCallback()) {

    class UserViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserProfile) {
            println("Binding user: $user")
            binding.apply {
                emailTextView.text = user.email
                firstNameTextView.text = user.first_name
                lastNameTextView.text = user.last_name

                Glide.with(root.context)
                    .load(user.avatar)
                    .into(avatarImageView)
            }
        }

    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserProfile>() {
        override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile) =
            oldItem == newItem
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
        holder.bind(getItem(position))
    }
}