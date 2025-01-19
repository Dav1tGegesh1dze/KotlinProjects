package com.example.task17

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task17.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun start() {
        observeViewModel()
        binding.loginButton.setOnClickListener {
            val email = binding.emailNameEditText.text.toString().trim()
            val password = binding.passwordNameEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(email, password)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            if (result == "Login Successful") {

                findNavController().popBackStack(R.id.loginFragment, true)
            }
        }
    }
}