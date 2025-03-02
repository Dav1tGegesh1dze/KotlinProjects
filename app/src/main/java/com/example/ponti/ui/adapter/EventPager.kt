package com.example.ponti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ponti.databinding.ItemEventBinding
import com.example.ponti.domain.Event
import java.text.SimpleDateFormat
import java.util.Locale

class EventPagerAdapter(
    private var events: List<Event>
) : RecyclerView.Adapter<EventPagerAdapter.EventViewHolder>() {

    class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tvTitle.text = event.title
            binding.tvDescription.text = event.description
            binding.tvLocation.text = event.location

            // Format date
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("ka", "GE"))
            val date = inputFormat.parse(event.date)
            date?.let {
                binding.tvDate.text = outputFormat.format(it)
            }

            // Format price range
            binding.tvPrice.text = "${event.startPrice} - ${event.endPrice} ₾"

            // Load image
            Glide.with(binding.root.context)
                .load(event.imageUrl)
                .centerCrop()
                .into(binding.ivEvent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}