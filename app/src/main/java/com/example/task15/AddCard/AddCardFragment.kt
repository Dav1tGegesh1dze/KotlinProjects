package com.example.task15.AddCard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.task15.BaseFragment
import com.example.task15.ListAdapter.Card
import com.example.task15.CardViewModel
import com.example.task15.R
import com.example.task15.ListAdapter.cardBackgrond
import com.example.task15.databinding.FragmentAddCardBinding
import java.util.*

class AddCardFragment : BaseFragment<FragmentAddCardBinding>(
    FragmentAddCardBinding::inflate
) {
    private val cardViewModel: CardViewModel by activityViewModels()
    private var cardBackground: cardBackgrond? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.cardTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            cardBackground = when (checkedId) {
                R.id.visaRadioButton -> cardBackgrond.Visa
                R.id.mastercardRadioButton -> cardBackgrond.Mastercard
                else -> null
            }
        }

        binding.addCardButton.setOnClickListener {
            val beforeNumber = binding.cardNumberEditText.text.toString()
            val cardNumber = beforeNumber.chunked(4).joinToString("-")
            val ownerName = binding.ownerNameEditText.text.toString()
            val beforeExpire = binding.expiryDateEditText.text.toString()
            val expiryDate = beforeExpire.chunked(2).joinToString ("/")
            val ccv = binding.ccvEditText.text.toString()

            if (cardNumber == null || ownerName.isBlank() || expiryDate == null || ccv.isBlank() || cardBackground == null) {
                Toast.makeText(requireContext(), "Fill everything", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val card = Card(
                id = UUID.randomUUID().hashCode(),
                cardNumber = cardNumber,
                ownerName = ownerName,
                expiryDate = expiryDate,
                cardImage = cardBackground!!,
                ccv = ccv
            )

            cardViewModel.addCard(card)
            Toast.makeText(requireContext(), "Card added!", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }
    }
}
