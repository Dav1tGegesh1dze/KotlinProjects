package com.example.test6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.test6.data.local.TourItem
import com.example.test6.databinding.ItemTourPagerBinding

class TourPagerAdapter : ListAdapter<TourItem, TourPagerAdapter.TourViewHolder>(TourDiffCallback()) {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        return TourViewHolder(
            ItemTourPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.apply {
            val scale = if (position == selectedPosition) 1f else 0.85f
            val alpha = if (position == selectedPosition) 1f else 0.6f
            scaleX = scale
            scaleY = scale
            this.alpha = alpha
        }
    }

    fun updateSelectedPosition(position: Int) {
        val oldPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(selectedPosition)
    }

    inner class TourViewHolder(
        private val binding: ItemTourPagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    updateSelectedPosition(position)
                }
            }
        }

        fun bind(item: TourItem) {
            binding.apply {
                titleTxt.text = item.title
                locationTxt.text = item.location
                priceTxt.text = item.price
                reactionsTxt.text = "${item.reactionCount} reactions"
                ratingBar.rating = item.rate?.toFloat() ?: 0f

                Glide.with(coverImg)
                    .load(item.cover)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .into(coverImg)
            }
        }
    }

    class TourDiffCallback : DiffUtil.ItemCallback<TourItem>() {
        override fun areItemsTheSame(oldItem: TourItem, newItem: TourItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TourItem, newItem: TourItem) =
            oldItem == newItem
    }
}