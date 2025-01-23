package com.example.task18.Fragments

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task18.R
import com.example.task18.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun start() {
        val email = viewModel.getStoredEmail()
        binding.emailTextView.text = "Email: , $email"

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_homeFragment_to_welcomeFragment)


    }
}}