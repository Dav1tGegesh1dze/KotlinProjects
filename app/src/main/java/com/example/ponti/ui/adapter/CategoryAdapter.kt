package com.example.ponti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ponti.R
import com.example.ponti.databinding.ItemCategoryBinding
import com.example.ponti.domain.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, position: Int) {
            binding.tvCategory.text = category.name

            if (position == selectedPosition) {
                binding.cardCategory.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.category_selected))
                binding.tvCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {
                binding.cardCategory.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
                binding.tvCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            }

            // change later to real categories.
            when (category.name) {
                "სპორტი" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_background)
                "კინო" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_foreground)
                "თეატრი" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_background)
                "მუსიკა" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_foreground)
                "ჰობი" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_background)
                "მუზეუმი" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_foreground)
                "პონტი" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_background)
                else -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_launcher_foreground)
            }

            binding.root.setOnClickListener {
                if (selectedPosition != position) {
                    val oldPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(oldPosition)
                    notifyItemChanged(selectedPosition)
                    onCategorySelected(category.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], position)
    }

    override fun getItemCount(): Int = categories.size
}