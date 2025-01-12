package com.example.task14.CompletedOrderFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task14.ListAdapter.Order
import com.example.task14.ListAdapter.OrderListAdapter
import com.example.task14.ListAdapter.OrderStatus
import com.example.task14.R
import com.example.task14.databinding.FragmentActiveOrdersBinding


class CompletedOrdersFragment : Fragment() {

    private lateinit var binding: FragmentActiveOrdersBinding
    private lateinit var ordersAdapter: OrderListAdapter
    private var isReviewFragmentOpened = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActiveOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadCompletedOrders()
    }

    private fun setupRecyclerView() {
        ordersAdapter = OrderListAdapter(
            onItemClicked = { order ->
                if (!isReviewFragmentOpened) {
                    val bundle = bundleOf("order" to order)
                    requireActivity().findNavController(R.id.nav_host_fragment).navigate(
                        R.id.reviewFragment, bundle
                    )
                    isReviewFragmentOpened = true
                }
            }
        )
        binding.activeOrdersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
        }
    }


    private fun loadCompletedOrders() {
        val completedOrders = listOf(
            Order(3, " Sofa", Color.RED, "Red", 3, OrderStatus.COMPLETED, 140.00, R.drawable.sofa),
            Order(4, "set 3", Color.WHITE, "White", 1, OrderStatus.COMPLETED, 900.00, R.drawable.furniture3)
        )
        ordersAdapter.submitList(completedOrders)
    }
}
