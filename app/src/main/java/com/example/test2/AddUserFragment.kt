package com.example.test2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.test2.databinding.FragmentAddUserBinding
import androidx.fragment.app.activityViewModels
import java.text.SimpleDateFormat
import java.util.*

class AddUserFragment : Fragment() {

    private var binding: FragmentAddUserBinding? = null
    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    //get time format
    private fun formatTimestampToDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        saveButtonClicked()
        return binding?.root
    }

    private fun saveButtonClicked() {
        binding?.addButton?.setOnClickListener {
            checkInputs()
            addToTheList()
        }
    }

    //Check inputs
    private fun checkInputs() {
        val id = binding!!.idEditText.text.toString()
        val firstName = binding!!.firstNameEditText.text.toString()
        val lastName = binding!!.lastNameEditText.text.toString()
        val birthday = binding!!.birthdayEditText.text.toString()
        val address = binding!!.emailEditText.text.toString()
        val email = binding!!.emailEditText.text.toString()

        if (id.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter ID correctly", Toast.LENGTH_SHORT).show()
            return
        }

        if (firstName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter First Name correctly", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (lastName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Last Name correctly", Toast.LENGTH_SHORT).show()
            return
        }

        if (birthday.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Birthday correctly", Toast.LENGTH_SHORT).show()
            return
        }

        if (address.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Address correctly", Toast.LENGTH_SHORT).show()
            return
        }

        if (email.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter a valid Email", Toast.LENGTH_SHORT).show()
            return
        }
    }

    //Changes time format and then , make everything object + Add to viewModel
    private fun addToTheList() {
        val birthdayInput = binding!!.birthdayEditText.text.toString()
        val formattedBirthday = try {
            val timestamp = birthdayInput.toLong()
            formatTimestampToDate(timestamp)
        } catch (e: NumberFormatException) {
            birthdayInput
        }

        val newUser = User(
            id = binding!!.idEditText.text.toString().toInt(),
            firstName = binding!!.firstNameEditText.text.toString(),
            lastName = binding!!.lastNameEditText.text.toString(),
            birthday = formattedBirthday,
            address = binding!!.emailEditText.text.toString(),
            email = binding!!.emailEditText.text.toString()
        )
        viewModel.addUser(newUser)
        Toast.makeText(requireContext(), "Added!", Toast.LENGTH_SHORT).show()
    }
}
