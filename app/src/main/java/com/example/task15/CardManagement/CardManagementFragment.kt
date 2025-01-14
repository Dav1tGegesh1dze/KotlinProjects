package com.example.task15.CardManagement

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task15.ListAdapter.CardListAdapter
import com.example.task15.databinding.FragmentCardManagementBinding
import androidx.navigation.fragment.findNavController
import com.example.task15.BaseFragment
import com.example.task15.CardViewModel
import com.example.task15.R

class CardManagementFragment : BaseFragment<FragmentCardManagementBinding>(
    FragmentCardManagementBinding::inflate
) {
    private lateinit var cardAdapter: CardListAdapter
    private val cardViewModel: CardViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadCards()
        navigateToAddFragment()
    }

    private fun initViews() {
        cardAdapter = CardListAdapter { card ->
            cardViewModel.removeCard(card)
            updateRecyclerView()
            Toast.makeText(requireContext(), "Card deleted", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = cardAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadCards() {
        cardAdapter.submitList(cardViewModel.cards.toList())
    }

    private fun updateRecyclerView() {
        cardAdapter.submitList(cardViewModel.cards.toList())
    }

    private fun navigateToAddFragment(){
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.addCardFragment)
        }
    }
}
