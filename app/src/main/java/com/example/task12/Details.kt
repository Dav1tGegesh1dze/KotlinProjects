package com.example.task12

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.task12.databinding.FragmentDetailsBinding

class Details : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var currentOrder: String? = null
    private var selectedStatus: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get args
        arguments?.let { args ->
            currentOrder = args.getString(ARG_ORDER)
            binding.trackingNumberValue.text = args.getString(ARG_TRACKING_NUMBER)
            binding.quantityValue.text = args.getInt(ARG_QUANTITY).toString()
            binding.priceValue.text = "$${args.getDouble(ARG_PRICE)}"
            selectedStatus = args.getString(ARG_STATUS, "Pending")
        }

        setupStatusSpinner()
        setupSaveButton()
    }

    private fun setupStatusSpinner() {
        val statusOptions = listOf("Pending", "Delivered", "Cancelled")
        val statusAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statusOptions
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.statusSpinner.adapter = statusAdapter

        val statusPosition = statusAdapter.getPosition(selectedStatus)
        binding.statusSpinner.setSelection(statusPosition)

        binding.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedStatus = statusOptions[position]

                Log.d("fragmebt", "it's: $selectedStatus")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val newStatus = binding.statusSpinner.selectedItem.toString()
            val orderId = arguments?.getString(ARG_ORDER)

            if (orderId != null) {
                val showInfoFragment = parentFragmentManager.fragments.firstOrNull {
                    it is ShowInfoFragment
                } as? ShowInfoFragment

                showInfoFragment?.let { fragment ->
                    Log.d("Details", "Updating order $orderId with status $newStatus")
                    fragment.updateOrderStatus(orderId, newStatus)
                }

                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ORDER = "order"
        private const val ARG_TRACKING_NUMBER = "tracking_number"
        private const val ARG_QUANTITY = "quantity"
        private const val ARG_PRICE = "price"
        private const val ARG_STATUS = "status"

        fun newInstance(
            order: String,
            trackingNumber: String,
            quantity: Int,
            price: Double,
            status: String
        ) = Details().apply {
            arguments = Bundle().apply {
                putString(ARG_ORDER, order)
                putString(ARG_TRACKING_NUMBER, trackingNumber)
                putInt(ARG_QUANTITY, quantity)
                putDouble(ARG_PRICE, price)
                putString(ARG_STATUS, status)
            }
        }
    }
}