package com.example.task12

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task12.databinding.ListItemBinding

class OrdersDiffUtil : DiffUtil.ItemCallback<Orders>() {
    override fun areItemsTheSame(oldItem: Orders, newItem: Orders): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Orders, newItem: Orders): Boolean {
        return oldItem.id == newItem.id
    }


}

class OrdersAdapter(private val listener: OrderClickListener) : ListAdapter<Orders, OrdersAdapter.OrderViewHolder>(OrdersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    inner class OrderViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Orders) {
            binding.apply {
                orderNumber.text = order.order
                orderDate.text = "Date: ${order.time}"
                trackingNumber.text = order.trackingNumber
                qualityValue.text = order.quantity.toString()
                subtotalValue.text = "$${order.price}"
                pendingText.text = order.status

                //go to second fragment
                detailsButton.setOnClickListener{
                    listener.onDetailsButtonClicked(order)

                }
            }
        }
    }
}

interface OrderClickListener {
    fun onDetailsButtonClicked(order: Orders)
}