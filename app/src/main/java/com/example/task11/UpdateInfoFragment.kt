package com.example.task11

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.task11.databinding.FragmentAddInfoBinding
import com.example.task11.databinding.FragmentUpdateInfoBinding


class UpdateInfoFragment : Fragment() {
    private var _binding: FragmentUpdateInfoBinding? = null
    private val binding get() = _binding!!
    private var addressToUpdate: RecyclerAddress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            addressToUpdate = it.getParcelable("selected_address")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressToUpdate?.let { address ->
            binding.editTextPlace.setText(address.place)
            binding.editTextDescription.setText(address.fullLocation)
        }

        binding.addInfoButton.setOnClickListener {
            val updatedPlace = binding.editTextPlace.text.toString()
            val updatedDescription = binding.editTextDescription.text.toString()

            addressToUpdate?.let { address ->
                val updatedAddress = address.copy(
                    place = updatedPlace,
                    fullLocation = updatedDescription,
                    isChecked = false
                )

                parentFragmentManager.setFragmentResult(
                    "update_key",
                    bundleOf("updated_address" to updatedAddress)
                )
            }

            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(address: RecyclerAddress) = UpdateInfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable("selected_address", address)
            }
        }
    }
}