package com.example.task23.presentation.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.task23.BaseFragment
import com.example.task23.DataStoreManager
import com.example.task23.R
import com.example.task23.data.local.Resource
import com.example.task23.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(DataStoreManager(requireContext()))
    }

    override fun start() {
        observeLoginState()
        setupListeners()
        autoFillFields()
    }

    private fun autoFillFields() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.savedEmail.collectLatest { email ->
                    binding.emailNameEditText.setText(email ?: "")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.savedPassword.collectLatest { password ->
                    binding.passwordNameEditText.setText(password ?: "")
                }
            }
        }
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailNameEditText.text.toString().trim()
            val password = binding.passwordNameEditText.text.toString().trim()
            val rememberMe = binding.rememberMeCheckBox.isChecked ?: false

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginViewModel.login(email, password, rememberMe)
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collectLatest { state ->
                    when (state) {
                        is Resource.Success -> {
                            if (binding.emailNameEditText.text.toString().trim() == "eve.holt@reqres.in") {
                                Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            } else {
                                Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                            }
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
}