package com.example.task17

import androidx.navigation.fragment.findNavController
import com.example.task17.databinding.FragmentWelcomeBinding


class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
) {


    override fun start(){
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.registerButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_welcomeFragment_to_registerFragment
            )
        }
        binding.loginButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_welcomeFragment_to_loginFragment
            )
        }
    }


}

