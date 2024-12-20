package com.example.test2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var usersFromFragment: MutableList<AddUserFragment.User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //   AddUserFragment
        binding.nextButton.setOnClickListener {
            val fragment = AddUserFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment, "tag")
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // Search  ID
        binding.saveButton.setOnClickListener {
            val searchText = binding.firstEditText.text.toString().trim().lowercase()

            if (searchText.isEmpty()) {
                Toast.makeText(this, " enter text to seacrch", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usersFromFragment.isEmpty()) {
                Toast.makeText(this, " add user ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Find it matches
            val foundUser = usersFromFragment.find { user ->
                user.id.toString().lowercase().contains(searchText) ||
                        user.firstName.lowercase().contains(searchText) ||
                        user.lastName.lowercase().contains(searchText) ||
                        user.email.lowercase().contains(searchText) ||
                        user.birthday.lowercase().contains(searchText) ||
                        user.address.lowercase().contains(searchText)
            }

            if (foundUser != null) {
                // Show use id
                Toast.makeText(this, "Found User ID: ${foundUser.id}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No User Found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // That was missing, to update and get it there.
    fun updateUsers(users: MutableList<AddUserFragment.User>) {
        usersFromFragment = users
    }
}
