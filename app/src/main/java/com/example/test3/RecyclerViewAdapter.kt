package com.example.test3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test3.databinding.RecyclerItemBinding

class RecyclerViewAdapter(
    private var items: List<GameDataClass>,
    private val onItemClick: (GameDataClass) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: RecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameDataClass) {

            binding.imageButton.text = if (item.mark == ' ') "" else item.mark.toString()
            binding.imageButton.isEnabled = item.mark == ' '

            binding.imageButton.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun getItems(): List<GameDataClass> = items

    fun updateData(newItems: List<GameDataClass>) {
        items = newItems
        notifyDataSetChanged()
    }
}
