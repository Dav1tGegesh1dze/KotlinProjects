package com.example.task11

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task11.databinding.AddressRecyclerviewLayoutBinding

class AddressDiffUtil: DiffUtil.ItemCallback<RecyclerAddress>(){
    override fun areItemsTheSame(oldItem: RecyclerAddress, newItem: RecyclerAddress): Boolean {
        return oldItem.id  == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecyclerAddress, newItem: RecyclerAddress): Boolean {
        return oldItem == newItem
    }

}

class AddressAdapter: ListAdapter<RecyclerAddress, AddressAdapter.AddressViewHolder>(AddressDiffUtil()) {

    var onCheckboxStateChanged: (() -> Unit)? = null
    var onItemLongPress: ((RecyclerAddress) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            AddressRecyclerviewLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.onBind()
    }

    inner class AddressViewHolder(val binding: AddressRecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)

            binding.locationImage.setImageResource(currentItem.locationImage)
            binding.editTextPlace.text = currentItem.place
            binding.fullAddress.text = currentItem.fullLocation

            binding.checkbox.isChecked = currentItem.isChecked

            // removelistener
            binding.checkbox.setOnCheckedChangeListener(null)

            // listener
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                currentItem.isChecked = isChecked
                onCheckboxStateChanged?.invoke()
            }

            itemView.setOnLongClickListener {
                onItemLongPress?.invoke(currentItem)
                true
            }
        }


    }

}