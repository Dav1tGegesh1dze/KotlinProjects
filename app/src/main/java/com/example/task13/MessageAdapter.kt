package com.example.task13

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task13.databinding.MessageItemBinding


class MessageAdapter : ListAdapter<MessageDetails, MessageAdapter.MessageViewHolder>(MessageDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message, position)
    }

    inner class MessageViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(messageDetails: MessageDetails, position: Int) {
            binding.messageText.text = messageDetails.message

            // Date and time
            val dateFormat = java.text.SimpleDateFormat("EEEE, HH:mm", java.util.Locale.getDefault())
            val formattedTime = dateFormat.format(java.util.Date(messageDetails.time))
            binding.timeText.text = formattedTime

            val layoutParams = binding.messageText.layoutParams as ViewGroup.MarginLayoutParams

            // check if there's position  even on odd
            if (position % 2 == 0) {
                binding.root.gravity = Gravity.START
                binding.messageText.setBackgroundResource(R.drawable.left_background)
                layoutParams.marginStart = 15
                layoutParams.marginEnd = 0
            } else {
                binding.root.gravity = Gravity.END
                binding.messageText.setBackgroundResource(R.drawable.right_background)
                layoutParams.marginStart = 0
                layoutParams.marginEnd = 15
            }
            binding.messageText.layoutParams = layoutParams
        }
    }
}

class MessageDiffUtil : DiffUtil.ItemCallback<MessageDetails>() {
    override fun areItemsTheSame(oldItem: MessageDetails, newItem: MessageDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageDetails, newItem: MessageDetails): Boolean {
        return oldItem == newItem
    }
}
