package com.example.task23.presentation.profile

import com.example.task23.databinding.ProfileItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task23.data.local.ProfileItem


class ProfileAdapter : androidx.paging.PagingDataAdapter<ProfileItem, ProfileAdapter.ProfileViewHolder>(
    ProfileDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = getItem(position)
        profile?.let { holder.bind(it) }
    }

    class ProfileViewHolder(private val binding: ProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: ProfileItem) {
            binding.apply {
                profileName.text = "${profile.firstName} ${profile.lastName}"
                profileEmail.text = profile.email

                Glide.with(profileAvatar.context)
                    .load(profile.avatar)
                    .centerCrop()
                    .into(profileAvatar)

            }
        }
    }

    class ProfileDiffCallback : DiffUtil.ItemCallback<ProfileItem>() {
        override fun areItemsTheSame(oldItem: ProfileItem, newItem: ProfileItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProfileItem, newItem: ProfileItem) = oldItem == newItem
    }
}
