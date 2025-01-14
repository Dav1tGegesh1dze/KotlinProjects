package com.example.task15.ListAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task15.R
import com.example.task15.databinding.ItemCardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}

class CardListAdapter(
    private val onItemClicked: ((Card) -> Unit)? = null
) : ListAdapter<Card, CardListAdapter.CardViewHolder>(CardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CardViewHolder(
        private val binding: ItemCardBinding,
        private val onItemClicked: ((Card) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.cardNumberTextView.text = card.cardNumber.toString()
            binding.ownerNameTextView.text = card.ownerName
            binding.expiryDateTextView.text = card.expiryDate.toString()

            val cardBackground = when (card.cardImage) {
                cardBackgrond.Visa -> R.drawable.visa_card
                cardBackgrond.Mastercard -> R.drawable.mastercard_card
            }
            binding.cardImageView.setImageResource(cardBackground)

            binding.root.setOnLongClickListener {
                val context = binding.root.context
                val bottomSheetDialog = BottomSheetDialog(context)
                val bottomSheetView = LayoutInflater.from(context).inflate(
                    R.layout.bottom_sheet,
                    null
                )
                bottomSheetDialog.setContentView(bottomSheetView)

                val yesButton = bottomSheetView.findViewById<Button>(R.id.yesButton)
                val noButton = bottomSheetView.findViewById<Button>(R.id.noButton)

                yesButton.setOnClickListener {
                    onItemClicked?.invoke(card)
                    bottomSheetDialog.dismiss()
                }

                noButton.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }

                bottomSheetDialog.show()
                true
            }
        }
    }
}
