package com.example.task27.presentation.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task27.R
import com.example.task27.databinding.ItemPostImageBinding

class PostImagesAdapter(private val images: List<String>) :
    RecyclerView.Adapter<PostImagesAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemPostImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(binding.ivPostImage)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivPostImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemPostImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size
}