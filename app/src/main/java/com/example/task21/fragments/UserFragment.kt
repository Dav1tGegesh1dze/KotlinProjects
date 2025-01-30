package com.example.task21.fragments

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task21.ProtoRepository
import com.example.task21.viewModel.UserViewModel
import com.example.task21.viewModel.UserViewModelFactory
import com.example.task21.databinding.FragmentUserBinding
import kotlinx.coroutines.launch

class UserFragment : BaseFragment<FragmentUserBinding>(
    FragmentUserBinding::inflate
) {
    private lateinit var protoRepository: ProtoRepository

    private val viewModel: UserViewModel by lazy {
        val repository = ProtoRepository(requireContext())
        val factory = UserViewModelFactory(repository)
        ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    override fun start() {
        protoRepository = ProtoRepository(requireContext())
        saveButton()
        readButton()
    }

    private fun saveButton(){
        binding.saveButton.setOnClickListener {

            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.email.text.toString()

            validateInputs()

            viewModel.updatePerson(firstName, lastName, email)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            binding.loginInfoLabel.visibility = View.GONE
            binding.firstNameTextView.visibility = View.GONE
            binding.lastNameTextView.visibility = View.GONE
            binding.emailTextView.visibility = View.GONE

            binding.firstName.text.clear()
            binding.lastName.text.clear()
            binding.email.text.clear()

        }
    }

    private fun validateInputs(){
        if(binding.firstName.text.isEmpty() or binding.lastName.text.isEmpty() or binding.email.text.isEmpty()){
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun readButton(){
        binding.readButton.setOnClickListener {
            binding.loginInfoLabel.visibility = View.VISIBLE
            binding.firstNameTextView.visibility = View.VISIBLE
            binding.lastNameTextView.visibility = View.VISIBLE
            binding.emailTextView.visibility = View.VISIBLE

            lifecycleScope.launch {
                protoRepository.personFlow.collect { person ->
                    binding.firstNameTextView.text = person.firstName
                    binding.lastNameTextView.text = person.lastName
                    binding.emailTextView.text = person.email
                }
            }
        }
    }

}