package com.example.ponti

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ponti.databinding.FragmentLogInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {

    private val viewModel: AuthViewModel by viewModels()

    override fun start() {
        setupClickListeners()
        observeAuthState()
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                email.isEmpty() -> showToast("Please enter email")
                password.isEmpty() -> showToast("Please enter password")
                else -> viewModel.signIn(email, password)
            }
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeAuthState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Loading -> showLoading()
                    is AuthState.Success -> {
                        hideLoading()
                        showToast("Login successful!")
                        findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                    }
                    is AuthState.Error -> {
                        hideLoading()
                        showToast(state.message)
                    }
                    else -> hideLoading()
                }
            }
        }
    }
}