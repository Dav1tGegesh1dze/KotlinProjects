package com.example.task11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task11.databinding.FragmentShowInfoBinding
import android.app.AlertDialog


class showInfoFragment : Fragment() {
    private var _binding: FragmentShowInfoBinding? = null
    private val binding get() = _binding!!
    private val addressAdapter by lazy { AddressAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        loadData()
        setUpAddButton()
        setUpFragmentResultListener()
        checkboxClick()
        updateButtonVisibility()

        setUpUpdateButton()
        setUpFragmentResultListener2()


    }

    private fun setUpRecyclerView() {
        binding.addressRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addressAdapter
        }
    }

    private fun loadData() {
        val addressList = listOf(
            RecyclerAddress(1, R.drawable.home_icon, "Home","Konfidencialuri informaciis 9b",false),
            RecyclerAddress(2, R.drawable.work_icon, "Work","Rustaveli avenue 9g",false),
            RecyclerAddress(3, R.drawable.other_icon, "University","Paata saakadzis 12",false)
        )
        addressAdapter.submitList(addressList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAddButton() {
        binding.addButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.show_info_fragment, AddInfoFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setUpFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { _, bundle ->
            val newItem = bundle.getParcelable<RecyclerAddress>("newItem")
            newItem?.let {
                addNewItem(it)
            }
        }
    }

    private fun addNewItem(newItem: RecyclerAddress) {
        val currentList = addressAdapter.currentList.toMutableList()
        newItem.id = currentList.size + 1
        currentList.add(newItem)
        addressAdapter.submitList(currentList)
    }

    private fun checkboxClick(){
        addressAdapter.onCheckboxStateChanged = {
            updateButtonVisibility()
        }
    }

    private fun updateButtonVisibility() {
        val anyChecked = addressAdapter.currentList.any { it.isChecked }
        binding.updateButton.visibility = if (anyChecked) View.VISIBLE else View.GONE
        binding.addButton.visibility = if (anyChecked) View.GONE else View.VISIBLE
    }


    private fun setUpUpdateButton() {
        binding.updateButton.setOnClickListener {
            val checkedItem = addressAdapter.currentList.find { it.isChecked }

            checkedItem?.let { address ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.show_info_fragment, UpdateInfoFragment.newInstance(address))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setUpFragmentResultListener2() {
        parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { _, bundle ->
            val newItem = bundle.getParcelable<RecyclerAddress>("newItem")
            newItem?.let {
                addNewItem(it)
            }
        }

        parentFragmentManager.setFragmentResultListener("update_key", viewLifecycleOwner) { _, bundle ->
            val updatedAddress = bundle.getParcelable<RecyclerAddress>("updated_address")
            updatedAddress?.let { address ->
                updateRecyclerView(address)
            }
        }
    }

    private fun updateRecyclerView(updatedAddress: RecyclerAddress) {
        val currentList = addressAdapter.currentList.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedAddress.id }
        if (index != -1) {
            currentList[index] = updatedAddress
            addressAdapter.submitList(currentList)
        }
        updateButtonVisibility()
    }

    private fun setUpDeleteListener() {
        addressAdapter.onItemLongPress = { address ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Address")
                .setMessage("Are you sure you want to delete this address?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteItem(address)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun deleteItem(addressToDelete: RecyclerAddress) {
        val currentList = addressAdapter.currentList.toMutableList()
        currentList.remove(addressToDelete)
        addressAdapter.submitList(currentList)
        updateButtonVisibility()
    }
}


