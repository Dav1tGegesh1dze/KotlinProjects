package com.example.task18.Fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.task18.R
import com.example.task18.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun start() {
        lifecycleScope.launch {
            try {
                val email = viewModel.getStoredEmail()
                binding.emailTextView.text = "Email: $email"
            } catch (e: Exception) {
                binding.emailTextView.text = "Failed to fetch email"
            }
        }

        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.welcomeFragment, true)
                    .build()

                findNavController().navigate(R.id.welcomeFragment, null, navOptions)
            }
        }

        binding.toProfiles.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }
}
