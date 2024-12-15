package com.example.taks7

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taks7.databinding.ActivityMain2Binding
import com.example.taks7.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.widget.Toast


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        setUpButtons()
    }

    private fun setUpButtons(){
        binding.backIcon.setOnClickListener{
            finish()
        }

        binding.seconPageLogInButton.setOnClickListener{
            inputValidation()
        }
    }

    private fun inputValidation(){
        if (binding.passwordEditTextLoginPage.text.toString().isNotEmpty() && binding.emailEditTextLoginPage.text.toString().isNotEmpty()){
            setupFirebaseLogIn()
        } else{
            binding.errorTextRegistration.text = "Write email and password correctly"
        }
    }
    private fun setupFirebaseLogIn(){
        val email = binding.emailEditTextLoginPage.text.toString()
        val password = binding.passwordEditTextLoginPage.text.toString()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information )
                    val user = firebaseAuth.currentUser
                    Toast.makeText(baseContext, "Authentication succesfull", Toast.LENGTH_SHORT).show()
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }


}