package com.example.test5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test5.databinding.ItemUserBinding
import com.example.test5.local.User

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                userName.text = "${user.first_name} ${user.last_name}"
                userAbout.text = user.about ?: ""

                //activate
                val status = when {
                    user.activation_status <= 0 -> "Not Activated"
                    user.activation_status == 1.0 -> "Online"
                    user.activation_status == 2.0 -> "Active few minutes ago"
                    user.activation_status in 2.1..22.9 -> "Active hours ago"
                    else -> "Unknown status"
                }
                userStatus.text = status

                if (!user.avatar.isNullOrEmpty()) {
                    Glide.with(avatar)
                        .load(user.avatar)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(avatar)
                } else {
                    avatar.setImageResource(R.drawable.ic_launcher_background)
                }
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}