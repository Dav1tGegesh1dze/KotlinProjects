package com.example.task12

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task12.databinding.FragmentShowInfoBinding
import java.sql.Time

class ShowInfoFragment : Fragment(), OrderClickListener {
    private var _binding: FragmentShowInfoBinding? = null
    private val binding get() = _binding!!
    private val ordersAdapter = OrdersAdapter(this)
    private var allOrders = listOf<Orders>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  recyclerView
        binding.recyclerView.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(context)
        }

       //add to recycler
        addData()
        // filter buttons
        setupFilterButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addData() {
        val ordersList = listOf(
            Orders(
                id = 1,
                order = "12345",
                time = Time(System.currentTimeMillis()),
                trackingNumber = "TN12345678",
                quantity = 2,
                price = 120,
                status = "Delivered"
            ),
            Orders(
                id = 2,
                order = "12346",
                time = Time(System.currentTimeMillis()),
                trackingNumber = "TN12345679",
                quantity = 3,
                price = 180,
                status = "Pending"
            ),
            Orders(
                id = 3,
                order = "12347",
                time = Time(System.currentTimeMillis()),
                trackingNumber = "TN12345680",
                quantity = 3,
                price = 18,
                status = "Cancelled"
            ),
            Orders(
                id = 4,
                order = "12348",
                time = Time(System.currentTimeMillis()),
                trackingNumber = "TN12345681",
                quantity = 6,
                price = 20,
                status = "Delivered"
            ),
            Orders(
                id = 5,
                order = "12349",
                time = Time(System.currentTimeMillis()),
                trackingNumber = "TN12345682",
                quantity = 1,
                price = 2,
                status = "Delivered"
            )
        )

        allOrders = ordersList
        ordersAdapter.submitList(ordersList)
    }

    override fun onDetailsButtonClicked(order: Orders) {
        val detailsFragment = Details.newInstance(
            order = order.order,
            trackingNumber = order.trackingNumber,
            quantity = order.quantity,
            price = order.price.toDouble(),
            status = order.status
        )

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment, "DetailsFragment")
            .addToBackStack("details")
            .commit()
    }

    fun updateOrderStatus(orderNumber: String, newStatus: String) {
        val updatedOrders = allOrders.map { order ->
            if (order.order == orderNumber) {
                order.copy(status = newStatus)
            } else {
                order
            }
        }

        allOrders = updatedOrders
        ordersAdapter.submitList(updatedOrders)
    }



    private fun updateFilterButtonStates(status: String) {
        binding.apply {
            btnPending.isSelected = false
            btnDelivered.isSelected = false
            btnCancelled.isSelected = false

            when (status) {
                "Pending" -> btnPending.isSelected = true
                "Delivered" -> btnDelivered.isSelected = true
                "Cancelled" -> btnCancelled.isSelected = true
            }
        }
    }

    private fun setupFilterButtons() {
        binding.apply {
            btnPending.setOnClickListener {
                updateFilterButtonStates("Pending")
                filterOrders("Pending")
            }
            btnDelivered.setOnClickListener {
                updateFilterButtonStates("Delivered")
                filterOrders("Delivered")
            }
            btnCancelled.setOnClickListener {
                updateFilterButtonStates("Cancelled")
                filterOrders("Cancelled")
            }
        }
    }

    private fun filterOrders(status: String) {
        val filteredList = allOrders.filter { it.status == status }
        ordersAdapter.submitList(null)
        ordersAdapter.submitList(filteredList)
    }


}