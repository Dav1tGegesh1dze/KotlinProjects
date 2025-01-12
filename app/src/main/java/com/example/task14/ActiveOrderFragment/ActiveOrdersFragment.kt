package com.example.task14.ActiveOrderFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task14.ListAdapter.Order
import com.example.task14.ListAdapter.OrderListAdapter
import com.example.task14.ListAdapter.OrderStatus
import com.example.task14.R
import com.example.task14.databinding.FragmentActiveOrdersBinding

class ActiveOrdersFragment : Fragment() {

    private lateinit var binding: FragmentActiveOrdersBinding
    private lateinit var ordersAdapter: OrderListAdapter

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
        loadActiveOrders()
    }

    private fun setupRecyclerView() {
        ordersAdapter = OrderListAdapter()
        binding.activeOrdersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
        }
    }

    private fun loadActiveOrders() {
        val activeOrders = listOf(
            Order(1, "Furniture 1", Color.WHITE, "White", 2, OrderStatus.ACTIVE, 280.00, R.drawable.furniture1),
            Order(2, "Furniture 2", Color.BLUE, "Blue", 1, OrderStatus.ACTIVE, 150.00, R.drawable.futniture2)
        )
        ordersAdapter.submitList(activeOrders)
    }
}
