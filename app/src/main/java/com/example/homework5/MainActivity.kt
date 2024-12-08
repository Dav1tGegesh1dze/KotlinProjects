package com.example.homework5

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homework5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //fix that when I press add its never empty , and not to let user with same email
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setUp()
    }

    private fun setUp() {
        //All visual componenets and one lsit for storing user input
        var personalInfoData = mutableListOf<String>()
        val nameEditText = binding.editName
        val emailEditText = binding.editEmail
        val addUserButton = binding.addUserButton
        var userInfoButton = binding.userInfoButton
        val userInformation = binding.userInformation
        val userNumber = binding.userCounter


        addUserButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            var user_counter = 0

            if (name.isNotEmpty() && email.isNotEmpty()) {
                var emailExists = false

                for (info in personalInfoData) {
                    // check same mail exists in our list
                    if (info.contains("email: $email")) {
                        emailExists = true
                        break
                    }
                }

                if (emailExists) {
                    userInformation.text = "Email already in the lise."
                } else {
                    val fullNameAndEmail = "name: $name, email: $email"
                    personalInfoData.add(fullNameAndEmail)
                }
            } else {
                userInformation.text = "Please enter both name and email."
            }

            // count the users in the lsit
            user_counter = personalInfoData.size
            userNumber.text = "Users -> $user_counter"
        }


        userInfoButton.setOnClickListener {
                if (personalInfoData.isNotEmpty()) {

                    var bigInformationScreen = ""
                    for (information in personalInfoData) {
                        //one string for all inputs from users
                        bigInformationScreen += "$information \n "

                    }
                    userInformation.text = bigInformationScreen
                } else {
                    userInformation.text = "User not found"

                }

            }

        }

    }
