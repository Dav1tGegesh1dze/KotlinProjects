package com.example.ponti

import androidx.navigation.fragment.findNavController
import com.example.ponti.databinding.FragmentLandingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : BaseFragment<FragmentLandingBinding>(
    FragmentLandingBinding::inflate
) {

    override fun start() {
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.googleSignInButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_googleSignInFragment)
        }

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_signUpFragment)
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_logInFragment)
        }
    }
}