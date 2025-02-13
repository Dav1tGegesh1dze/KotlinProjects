package com.example.task23.presentation.home

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.task23.BaseFragment
import com.example.task23.R
import com.example.task23.databinding.FragmentHomeBinding
import com.example.task23.presentation.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        setupUI()
        setupListeners()
        stayOnFragment()
    }

    private fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.savedEmail.collectLatest { email ->
                    binding.emailText.text = "Email: ${email ?: ""}"
                }
            }
        }
    }

    private fun setupListeners() {
        binding.logOutButton.setOnClickListener {
            loginViewModel.clearSession()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        binding.profileButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun stayOnFragment() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }
}