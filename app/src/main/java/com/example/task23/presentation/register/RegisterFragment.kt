package com.example.task23.presentation.register

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
import com.example.task23.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun start() {
        observeRegisterState()
        setupListeners()
        handleBackPress()
    }

    override fun onResume() {
        super.onResume()
        registerViewModel.resetState()
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_registerFragment_to_landingPageFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailNameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val passwordRepeat = binding.repeatPasswordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
                showToast("Please fill in all fields")
                return@setOnClickListener
            }

            if (password != passwordRepeat) {
                showToast("Passwords do not match")
                return@setOnClickListener
            }

            if (email != "eve.holt@reqres.in") {
                showToast("Invalid email")
                return@setOnClickListener
            }

            registerViewModel.register(email, password)
        }
    }

    private fun showToast(message: String) {
        if (isAdded && !isDetached) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.registerState.collectLatest { state ->
                    when (state) {
                        is Resource.Success -> {
                            showToast("Registration successful!")
                            kotlinx.coroutines.delay(300)
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            registerViewModel.resetState()
                        }
                        is Resource.Error -> {
                            showToast(state.errorMessage)
                            registerViewModel.resetState()
                        }
                        null -> {  }
                    }
                }
            }
        }
    }
}