package com.example.task11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.task11.databinding.FragmentAddInfoBinding

class AddInfoFragment : Fragment() {
    private var _binding: FragmentAddInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAddButton()
    }

    private fun setUpAddButton() {
        binding.addInfoButton.setOnClickListener {
            val address = binding.editTextDescription.text.toString()
            val place = binding.editTextPlace.text.toString()
            if (address.isBlank() || place.isBlank()) {
                Toast.makeText(requireContext(), "Address or place cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var sizeCurrentSize = 3
            sizeCurrentSize++
            val newItem = RecyclerAddress(sizeCurrentSize, R.drawable.other_icon, place ,address,false)

            val resultBundle = Bundle().apply {
                putParcelable("newItem", newItem)
            }
            parentFragmentManager.setFragmentResult("requestKey", resultBundle)
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
