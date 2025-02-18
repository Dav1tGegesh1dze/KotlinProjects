package com.example.task23.presentation.landingpage

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.task23.BaseFragment
import com.example.task23.R
import com.example.task23.databinding.FragmentLandingPageBinding
import com.example.task23.presentation.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LandingPageFragment : BaseFragment<FragmentLandingPageBinding>(FragmentLandingPageBinding::inflate) {


    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        goToRegisterFragment()
        goToLoginFragment()
        checkRememberMe()
    }

    private fun checkRememberMe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.isRemembered.collectLatest { remembered ->
                    if (remembered == true) {
                        try {
                            findNavController().navigate(R.id.action_landingPageFragment_to_homeFragment)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }


    private fun goToRegisterFragment() {
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingPageFragment_to_registerFragment)
        }
    }

    private fun goToLoginFragment() {
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingPageFragment_to_loginFragment)
        }
    }
}
