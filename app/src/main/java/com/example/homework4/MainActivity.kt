package com.example.homework4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val titleText: TextView = findViewById(R.id.titleText)
        val editEmailText: EditText = findViewById(R.id.emailText)
        val editUsernameText: EditText = findViewById(R.id.usernameText)
        val editFirstNameText: EditText = findViewById(R.id.firstNameText)
        val editLastNameText: EditText = findViewById(R.id.lastNameText)
        val editAgeText: EditText = findViewById(R.id.ageText)
        val saveText: Button = findViewById(R.id.saveButton)
        val clearText: Button = findViewById(R.id.clearButton)
        val errorText: TextView = findViewById(R.id.errorMessage)
        val againButton: Button = findViewById(R.id.againButton)

        saveText.setOnClickListener {
            val email = editEmailText.text.toString()
            val username = editUsernameText.text.toString()
            val firstName = editFirstNameText.text.toString()
            val lastName = editLastNameText.text.toString()
            val ageText = editAgeText.text.toString()
            val age = ageText.toInt()

            // Check if everything is written correcyly
            var isCorrect = true

            // Validation
            if (email.isNullOrEmpty() || username.isNullOrEmpty() || firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || ageText.isNullOrEmpty()) {
                errorText.text = "Everything should be filled"
                errorText.visibility = View.VISIBLE
                isCorrect = false
            }

            if (email.contains("@") && (email.endsWith(".com") || email.endsWith(".ge"))) {
                println("Email is correct")
            } else {
                errorText.text = "Email is not correct"
                errorText.visibility = View.VISIBLE
                isCorrect = false
            }

            if (username.length < 10) {
                errorText.text = "Username has less than 10 symbols"
                errorText.visibility = View.VISIBLE
                isCorrect = false
            }

            if (age <= 0) {
                errorText.text = "Age should be positive number"
                errorText.visibility = View.VISIBLE
                isCorrect = false
            }

            // If everything is correct update
            if (isCorrect) {
                errorText.visibility = View.GONE
                editEmailText.visibility = View.GONE
                editUsernameText.visibility = View.GONE
                editFirstNameText.visibility = View.GONE
                editLastNameText.visibility = View.GONE
                editAgeText.visibility = View.GONE
                saveText.visibility = View.GONE
                clearText.visibility = View.GONE

                // Text of EditTexst
                titleText.text = "Personal Info:\n" +
                        "Email: $email\n" +
                        "Username: $username\n" +
                        "Name: $firstName" + " "+"$lastName\n" +
                        "Age: $age"

                // We can see againButton as soon as Everything ic correct
                againButton.visibility = View.VISIBLE
            }
        }

        againButton.setOnClickListener {
            // Everything comes back again
            editEmailText.visibility = View.VISIBLE
            editUsernameText.visibility = View.VISIBLE
            editFirstNameText.visibility = View.VISIBLE
            editLastNameText.visibility = View.VISIBLE
            editAgeText.visibility = View.VISIBLE
            saveText.visibility = View.VISIBLE
            clearText.visibility = View.VISIBLE
            againButton.visibility = View.GONE

            // Reset every text, make them clear again
            editEmailText.text.clear()
            editUsernameText.text.clear()
            editFirstNameText.text.clear()
            editLastNameText.text.clear()
            editAgeText.text.clear()
            titleText.text = "profile info"
        }

        clearText.setOnLongClickListener {
            editEmailText.text.clear()
            editUsernameText.text.clear()
            editFirstNameText.text.clear()
            editLastNameText.text.clear()
            editAgeText.text.clear()
            true
        }
    }
}
