package com.example.task8
import androidx.core.content.ContextCompat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task8.databinding.ActivityMainBinding

data class User(
    val firstName:String,
    val lastName:String,
    val age:Int,
    val email:String
)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val users = mutableListOf<User>()
    private var deletedUserCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addUserButton()
        removeUserButton()
        updateUserButton()
        updateActiveUsersCounter()
        updateDeletedUsersCounter()
    }

    //add user logic + clearIUSer inputs
    private fun addUserButton(){
        binding.addUserButton.setOnClickListener{
            addUser()
            clearInputFields()
        }
    }

    //clear all inputs
    private fun clearInputFields() {
        binding.firstNameEditText.text.clear()
        binding.lastNameEditText.text.clear()
        binding.ageEditText.text.clear()
        binding.emailEditText.text.clear()
    }
    //remove user
    private fun removeUserButton(){
        binding.removeUserButton.setOnClickListener{
            removeUser()
            clearInputFields()
        }
    }
    //update user
    private fun updateUserButton(){
        binding.updateUserButton.setOnClickListener{
            updateUser()
            clearInputFields()
        }
    }

    //validate if all fields are filled correctly + logic of add
    private fun addUser() {
        //ვიცი ცუდი პრაქტიკაა, თან სხვადასხვა ფუნქციაში თავიდან მიწევს , მაგრამ დიდი if ების დროს მოსახერხებელია ჩემთვის
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.firstNameEditText.text.toString()
        val ageText = binding.ageEditText.text.toString()
        val age = ageText.toIntOrNull()
        val email = binding.emailEditText.text.toString()

        //validation on inputs
        if (firstName.isBlank()) {
            binding.errorAcessTextView.text = "Write First name correctly"
            binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
            return
        }

        if (lastName.isBlank()) {
            binding.errorAcessTextView.text = "Write Last name correctly"
            binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
            return
        }

        if (ageText.isBlank()) {
            binding.errorAcessTextView.text = "Age cannot be empty"
            binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
            return
        }

        if (age == null || age < 18 || age > 130) {
            binding.errorAcessTextView.text = "Age must be a number between 18 and 130"
            binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
            return
        }

        if (email.isBlank()) {
            binding.errorAcessTextView.text = "Email cannot be empty"
            binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
            return
        }

        if (!email.contains("@") ||
            !email.contains(".") ||
            !(email.endsWith(".com") || email.endsWith(".ge")) ||
            !(email.contains("gmail") || email.contains("yahoo"))) {
            binding.errorAcessTextView.text = "Please enter a valid Gmail or Yahoo email"
            binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
            return
        }

        val newUser = User(firstName, lastName, age, email)

        //add logic
        for (user in users) {
            if (user.email == email ) {
                binding.errorAcessTextView.text = "User with this email is already in the list"
                binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
                return
            }
        }

        users.add(newUser)
        binding.errorAcessTextView.text = "User added successfully"
        binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.access_text_color))
        updateActiveUsersCounter()
    }

    //remove user
    private fun removeUser() {
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.firstNameEditText.text.toString()
        val ageText = binding.ageEditText.text.toString()
        val age = ageText.toIntOrNull()
        val email = binding.emailEditText.text.toString()

        for (user in users) {
            if (user.email == email && user.age == age && user.lastName == lastName && user.firstName == firstName) {
                binding.errorAcessTextView.text = "User deleted"
                binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.access_text_color))
                deletedUserCount++
                clearInputFields()
                users.remove(user)
                updateActiveUsersCounter()
                updateDeletedUsersCounter()
                break
            } else {
                binding.errorAcessTextView.text = "User not found"
                binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
            }
        }
    }

    //update user code
    private fun updateUser(){
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.firstNameEditText.text.toString()
        val ageText = binding.ageEditText.text.toString()
        val age = ageText.toIntOrNull()
        val email = binding.emailEditText.text.toString()

        for (i in users.indices) {
            val user = users[i]
            if (user.email == email) {
                if (user.firstName != firstName ||
                    user.lastName != lastName ||
                    user.age != age
                ) {
                    val updatedUser = User(firstName,lastName,age?:user.age,email)

                    users[i] = updatedUser
                    clearInputFields()
                    binding.errorAcessTextView.text = "User Updated"
                    binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.access_text_color))
                    return
                } else {
                    binding.errorAcessTextView.text = "User not matches with mail"
                    binding.errorAcessTextView.setTextColor(ContextCompat.getColor(this, R.color.error_text_color))
                    return
                }
            }
        }
    }
    //counter for actice user
    private fun updateActiveUsersCounter() {
        binding.activeUser.text = "Active Users: ${users.size}"
    }

    //counter for deleted users
    private fun updateDeletedUsersCounter() {
        binding.deletedUser.text = "Deleted Users: $deletedUserCount"
    }
}