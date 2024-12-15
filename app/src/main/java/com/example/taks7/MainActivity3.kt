package com.example.taks7

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taks7.databinding.ActivityMain3Binding
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.widget.Toast




class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        backButtonClicked()
        nextButton()
    }

    //click back
    private fun backButtonClicked(){
        binding.backIcon2.setOnClickListener{
            finish()
        }
    }
    //next clicked -goes to new activity
    private fun nextButton(){
        binding.secondPageNextButton.setOnClickListener {
            nextButtonCheck()
            openNextActivity()
        }
    }

    //next button clicked logic
    private var isCorrectlyWritten = false
    private fun nextButtonCheck(): Boolean {
        val email = binding.emailEditTextRegisterPage.text.toString().trim()
        val password = binding.passwordEditTextRegisterPage.text.toString()

        // check if password or email is empty
        if (email.isEmpty() || password.isEmpty()) {
            binding.errorTextRegistration.text = "Email and password cannot be empty."
            isCorrectlyWritten = false
            return isCorrectlyWritten
        }

        // check password length
        if (password.length <= 5) {
            binding.errorTextRegistration.text = "Password must 6+ chars long."
            isCorrectlyWritten = false
            return isCorrectlyWritten
        }

        // check if email is written in correct format
        val isEmailValid = (email.contains("gmail") || email.contains("yahoo") && email.contains("@")) &&
                (email.endsWith(".com") || email.endsWith(".ge"))

        if (!isEmailValid) {
            binding.errorTextRegistration.text = "incorrect email format"
            isCorrectlyWritten = false
            return isCorrectlyWritten
        }

        // everything correct
        isCorrectlyWritten = true
        binding.errorTextRegistration.text = "" // Clear any previous error message
        return isCorrectlyWritten
    }



    //go to next mainActivity Logic
    private fun openNextActivity(){
        if(isCorrectlyWritten){
            val intent = Intent(this, MainActivity4::class.java)
            fireBaseCodeRegister()
            startActivity(intent)
        }
    }

    private fun fireBaseCodeRegister(){
        val email = binding.emailEditTextRegisterPage.text.toString()
        val password = binding.passwordEditTextRegisterPage.text.toString()
        // copied this code from the firebase website
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success

                    val user = firebaseAuth.currentUser
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    openNextActivity()
                } else {
                    // Registration failure

                    Toast.makeText(
                        this,
                        task.exception?.localizedMessage ?: "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}