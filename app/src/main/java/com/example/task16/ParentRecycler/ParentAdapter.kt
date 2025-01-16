package com.example.task16.ParentRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task16.ChildRecycler.ChildAdapter
import com.example.task16.databinding.ItemFieldGroupBinding

class ParentAdapter(
    private var fieldGroups: List<FieldGroup>
) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(private val binding: ItemFieldGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fieldGroup: FieldGroup) {


            val childAdapter = ChildAdapter()
            binding.childRecyclerView.apply {
                adapter = childAdapter
                layoutManager = LinearLayoutManager(itemView.context)
                setHasFixedSize(true)
            }

            childAdapter.submitList(fieldGroup.fields)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ItemFieldGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(fieldGroups[position])
    }

    override fun getItemCount(): Int = fieldGroups.size


    fun updateList(newFieldGroups: List<FieldGroup>) {
        fieldGroups = newFieldGroups
        notifyDataSetChanged()
    }
}
