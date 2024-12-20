package com.example.test2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.test2.databinding.FragmentAddUserBinding

class AddUserFragment : Fragment() {
    data class User(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val birthday: String,
        val address: String,
        val email: String
    )

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private val users = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)

        // Add user to the list
        binding.addButton.setOnClickListener {
            val idText = binding.idEditText.text.toString()
            if (idText.isEmpty()) {
                Toast.makeText(context, "Please enter an ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = idText.toIntOrNull()
            if (id == null) {
                Toast.makeText(context, "Id: ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val birthday = binding.birthdayEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if (firstName.isBlank() || lastName.isBlank()) {
                Toast.makeText(context, "Write in every field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(id, firstName, lastName, birthday, address, email)
            users.add(user)
            //checking that exists
            Toast.makeText(context, "User size: ${users.size}", Toast.LENGTH_SHORT).show()

            //send to activity
            (activity as? MainActivity)?.updateUsers(users)

            // Clear input fields
            binding.idEditText.text.clear()
            binding.firstNameEditText.text.clear()
            binding.lastNameEditText.text.clear()
            binding.birthdayEditText.text.clear()
            binding.addressEditText.text.clear()
            binding.emailEditText.text.clear()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
