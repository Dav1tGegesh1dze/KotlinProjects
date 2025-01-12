package com.example.task14.ReviewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task14.ListAdapter.Order
import com.example.task14.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {
    private lateinit var binding: FragmentReviewBinding
    private var order: Order? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        order = arguments?.getSerializable("order") as? Order


        order?.let {
            binding.orderImageView.setImageResource(it.imageRes)
            binding.titleTextView.text = it.title
            binding.colorIndicator.setBackgroundColor(it.color)
            binding.colorTextView.text = it.colorName
            binding.quantityTextView.text = "Qty = ${it.quantity}"
            binding.statusTextView.text = it.status.name
            binding.priceTextView.text = "$${it.price}"
        }


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        )
    }
}
