package com.example.task16.ChildRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task16.R
import com.example.task16.databinding.ItemFieldBinding

class ChildAdapter :
    ListAdapter<Field, ChildAdapter.ChildViewHolder>(ChildDiffCallback()) {

    class ChildDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<Field>() {
        override fun areItemsTheSame(oldItem: Field, newItem: Field): Boolean {
            return oldItem.fieldId == newItem.fieldId
        }

        override fun areContentsTheSame(oldItem: Field, newItem: Field): Boolean {
            return oldItem == newItem
        }
    }


    inner class ChildViewHolder(private val binding: ItemFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(field: Field) {

            binding.editText1.hint = field.hint

            if (!field.iconUrl.isNullOrEmpty()) {
                binding.image1.setImageResource(R.drawable.ic_launcher_background)
                binding.image1.visibility = View.VISIBLE
            } else {
                binding.image1.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ItemFieldBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


