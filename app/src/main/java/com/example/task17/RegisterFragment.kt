package com.example.task17

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.task17.databinding.FragmentRegisterBinding
import androidx.navigation.fragment.findNavController


class RegisterFragment: BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun start() {
        observeViewModel()
        binding.registerButton.setOnClickListener {
            val email = binding.emailNameEditText.text.toString().trim()
            val password = binding.passwordNameEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.register(email, password)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            if (result == "Registration Successful") {
                findNavController().popBackStack(R.id.registerFragment, true)
            }
        }
    }
}