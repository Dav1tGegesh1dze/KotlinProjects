package com.example.task9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task9.databinding.FragmentSecondLogInBinding
import android.widget.Toast
import android.text.InputType

//second fragment for sign in
class SecondLogInFragment : Fragment() {
    private lateinit var binding: FragmentSecondLogInBinding
    private var savedEmail = ""
    private var savedPassword = ""
    var passwordShowing = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondLogInBinding.inflate(inflater, container, false)

        // Listen for the result of lifecycle get info of that. And save get strings from bundle from another fragment
        parentFragmentManager.setFragmentResultListener("user_credentials", viewLifecycleOwner) { _, bundle ->
            savedEmail = bundle.getString("saved_email", "")
            savedPassword = bundle.getString("saved_password", "")
        }
        signUpButton()
        passwordShowButton()
        return binding?.root
    }
    private fun signUpButton(){
        binding.signUpFragment.setOnClickListener{
            checkCredentials()
        }
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

    private fun checkCredentials() {
        val typedEmail = binding?.emailEditText?.text.toString()
        val typedPassword = binding?.passwordEditText?.text.toString()

        if (typedEmail == savedEmail && typedPassword == savedPassword) {
            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }

}