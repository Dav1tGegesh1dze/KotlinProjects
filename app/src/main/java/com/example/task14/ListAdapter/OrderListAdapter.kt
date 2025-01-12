package com.example.task14.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.task14.databinding.ItemOrderBinding

class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

class OrderListAdapter(
    private val onItemClicked: ((Order) -> Unit)? = null,
    private val onLeaveReviewClicked: ((Order) -> Unit)? = null
) : ListAdapter<Order, OrderListAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding, onItemClicked, onLeaveReviewClicked)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderViewHolder(
        private val binding: ItemOrderBinding,
        private val onItemClicked: ((Order) -> Unit)?,
        private val onLeaveReviewClicked: ((Order) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.orderImageView.setImageResource(order.imageRes)
            binding.titleTextView.text = order.title
            binding.colorIndicator.setBackgroundColor(order.color)
            binding.colorTextView.text = order.colorName
            binding.quantityTextView.text = "Qty = ${order.quantity}"
            binding.statusTextView.text = order.status.name
            binding.priceTextView.text = "$${order.price}"

            //leave review
            binding.leaveReviewButton.setOnClickListener {
                onLeaveReviewClicked?.invoke(order)
            }

            //  Click
            binding.root.setOnClickListener {
                onItemClicked?.invoke(order)
            }
        }
    }



}
