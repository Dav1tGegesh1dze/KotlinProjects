package com.example.task27.presentation.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.task27.data.models.PostDto
import com.example.task27.R
import com.example.task27.databinding.ItemPostBinding
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val posts = mutableListOf<PostDto>()

    fun updateData(newPosts: List<PostDto>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostDto) {
            Log.d("PostAdapter", "Binding post with name: ${post.owner.firstName} ${post.owner.lastName}")
            binding.apply {
                val ownerFirstName = post.owner.firstName
                val ownerLastName = post.owner.lastName
                Log.d("PostAdapter", "Raw names: $ownerFirstName $ownerLastName")
                tvOwnerName.text = "$ownerFirstName $ownerLastName".trim()


                val date = Date(post.owner.postDate * 1000L)
                val formattedDate = SimpleDateFormat("dd MMM 'at' h:mm a", Locale.getDefault())
                    .format(date)
                tvPostDate.text = formattedDate

                Glide.with(ivProfile)
                    .load(post.owner.profile)
                    .apply(RequestOptions().circleCrop())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivProfile)

                post.images?.let { imageUrls ->
                    vpImages.visibility = View.VISIBLE
                    vpImages.adapter = PostImagesAdapter(imageUrls)
                    dotsIndicator.visibility = if (imageUrls.size > 1) View.VISIBLE else View.GONE
                    dotsIndicator.setViewPager2(vpImages)
                } ?: run {
                    vpImages.visibility = View.GONE
                    dotsIndicator.visibility = View.GONE
                }

                tvLikes.text = post.likes.toString()
                tvComments.text = post.comments.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size
}