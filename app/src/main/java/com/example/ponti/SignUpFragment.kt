package com.example.ponti

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ponti.databinding.FragmentSignUpBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {

    private val viewModel: AuthViewModel by viewModels()

    override fun start() {
        setupClickListeners()
        observeAuthState()
    }

    private fun setupClickListeners() {
        binding.dateOfBirthEditText.setOnClickListener {
            showDatePicker()
        }

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val dateOfBirth = binding.dateOfBirthEditText.text.toString()

            when {
                firstName.isEmpty() -> showToast("Please enter first name")
                lastName.isEmpty() -> showToast("Please enter last name")
                email.isEmpty() -> showToast("Please enter email")
                password.isEmpty() -> showToast("Please enter password")
                dateOfBirth.isEmpty() -> showToast("Please enter date of birth")
                else -> viewModel.signUp(email, password, firstName, lastName, dateOfBirth)
            }
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeAuthState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Loading -> showLoading()
                    is AuthState.Success -> {
                        hideLoading()
                        showToast("Sign up successful!")
                        findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
                    }
                    is AuthState.Error -> {
                        hideLoading()
                        showToast(state.message)
                    }
                    else -> hideLoading()
                }
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                binding.dateOfBirthEditText.setText("$day/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
