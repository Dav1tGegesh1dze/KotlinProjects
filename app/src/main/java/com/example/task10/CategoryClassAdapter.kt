package com.example.task10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.task10.databinding.ShoppingCategoriesBinding

class CategoryClassAdapter(
    private val categoryList: MutableList<CategoryClass>, // List of categories
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryClassAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: ShoppingCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ShoppingCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.binding.categoryText.text = currentItem.categoryTextView


        holder.binding.root.setOnClickListener {
            onCategorySelected(currentItem.categoryTextView)
        }
    }
}
