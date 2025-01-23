package com.example.task18.Fragments

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task18.R
import com.example.task18.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun start() {
        observeViewModel()
        binding.registerButton.setOnClickListener {
            val email = binding.emailNameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val passwordRepeat = binding.repeatPasswordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.empty_fields_message) , Toast.LENGTH_SHORT).show()

            }
            if( password != passwordRepeat){
                Toast.makeText(requireContext(), getString(R.string.passwords_do_not_match_message), Toast.LENGTH_SHORT).show()
            } else {
                parentFragmentManager.setFragmentResult("registration_key", bundleOf(
                    "email" to email,
                    "password" to password
                ))

                viewModel.register(email, password)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()

            if (result == getString(R.string.registration_success_message)) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }
}