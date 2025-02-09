package com.example.task23.presentation.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.task23.BaseFragment
import com.example.task23.DataStoreManager
import com.example.task23.R
import com.example.task23.data.local.Resource
import com.example.task23.databinding.FragmentRegisterBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(DataStoreManager(requireContext()))
    }

    override fun start() {
        observeRegisterState()
        setupListeners()
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailNameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val passwordRepeat = binding.repeatPasswordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != passwordRepeat) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email != "eve.holt@reqres.in") {
                Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(email, password)
        }
    }

    private fun observeRegisterState() {
        lifecycleScope.launch {
            registerViewModel.registerState.collectLatest { state ->
                when (state) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    null -> {}
                }
            }
        }
    }
}
