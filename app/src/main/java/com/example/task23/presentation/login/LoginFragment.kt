package com.example.task23.presentation.login

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.task23.BaseFragment
import com.example.task23.R
import com.example.task23.data.local.Resource
import com.example.task23.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        observeLoginState()
        setupListeners()
        autoFillFields()
        handleBackPress()
    }

    override fun onResume() {
        super.onResume()
        loginViewModel.resetLoginState()
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
            val rememberMe = binding.rememberMeCheckBox.isChecked

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill in all fields")
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
                            showToast("Login successful!")
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        is Resource.Error -> {
                            showToast(state.errorMessage)
                        }
                        null -> {  }
                    }
                }
            }
        }
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_loginFragment_to_landingPageFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun showToast(message: String) {
        if (isAdded) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}