package com.example.task10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task10.databinding.ShoppingListItemBinding

class ProductClassAdapter(var productList: MutableList<ProductDataClass>) :
    RecyclerView.Adapter<ProductClassAdapter.ProductViewHolder>() {


    class ProductViewHolder(val binding: ShoppingListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ShoppingListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.binding.productImage.setImageResource(currentItem.mainImage)
        holder.binding.heartImage.setImageResource(currentItem.heartImage)
        holder.binding.description.text = currentItem.productDescription
    }
}
