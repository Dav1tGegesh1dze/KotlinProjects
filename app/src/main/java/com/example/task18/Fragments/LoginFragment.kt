package com.example.task18.Fragments

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task18.R
import com.example.task18.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun start() {
        // fragment result listener
        parentFragmentManager.setFragmentResultListener(
            "registration_key",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("email") ?: ""
            val password = bundle.getString("password") ?: ""

            // login fields filled automatically
            binding.emailNameEditText.setText(email)
            binding.passwordNameEditText.setText(password)
        }

        observeViewModel()
        binding.loginButton.setOnClickListener {
            val email = binding.emailNameEditText.text.toString().trim()
            val password = binding.passwordNameEditText.text.toString().trim()
            val rememberMe = binding.rememberMeCheckBox.isChecked

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.empty_fields_message), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(email, password, rememberMe)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()

            if (result == getString(R.string.login_success_message)) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}