package com.example.task9

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task9.databinding.FragmentFirstSignWithPasswordBinding
import android.widget.Toast


data class UserInfo(
    val email: String,
    val password: String
)
//first fragment that lets to register and save login to lst
class FirstSignWithPasswordFragment : Fragment() {
    var passwordShowing = false
    private lateinit var binding: FragmentFirstSignWithPasswordBinding
    val usersList = mutableListOf<UserInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstSignWithPasswordBinding.inflate(inflater, container, false)
        clickSignUp()
        passwordShowButton()
        return binding?.root
    }

    private fun passwordShowButton(){
        binding.passwordShowSymbol.setOnClickListener{
            passwordShowHide()
        }
    }

    //hide or show password
    private fun passwordShowHide(){

        val passwordEditText = binding?.passwordEditText?.text.toString()
        if (passwordShowing){
            binding.passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordShowing = false
            return
        } else {
            // Show password
            binding.passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordShowing = true
            return
        }
    }
    private fun clickSignUp(){
        binding!!.signUpFragment.setOnClickListener{
            saveUserInfo()
        }
    }

    private fun saveUserInfo() {
        val emailText = binding?.emailEditText?.text.toString()
        val passwordText = binding?.passwordEditText?.text.toString()

        if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
            val newUser = UserInfo(emailText, passwordText)
            usersList.add(newUser)
            val bundle = Bundle()
            // You need both a key and a value for bundle
            bundle.putString("saved_email", emailText)
            bundle.putString("saved_password", passwordText)

            // Set the bundle to the fragment manager so other fragments can access it
            parentFragmentManager.setFragmentResult("user_credentials", bundle)

            Toast.makeText(requireContext(), "Account Created", Toast.LENGTH_SHORT).show()

            // Clear the input fields
            binding?.emailEditText?.text?.clear()
            binding?.passwordEditText?.text?.clear()

            // Navigate back using FragmentManager
            parentFragmentManager.popBackStack() // Pops the current fragment from the back stack
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

}