package com.example.test2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.test2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            val fragment = AddUserFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, "ADD_USER_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        binding.saveButton.setOnClickListener {
            val searchText = binding.firstEditText.text.toString().trim().lowercase()

            if (searchText.isEmpty()) {
                Toast.makeText(this, "Please enter search text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fragment = supportFragmentManager.findFragmentByTag("ADD_USER_FRAGMENT") as? AddUserFragment
            if (fragment != null) {
                val users = fragment.getUsersList()
                val found = users.any { user ->
                    user.id.toString().lowercase().contains(searchText) ||
                            user.firstName.lowercase().contains(searchText) ||
                            user.lastName.lowercase().contains(searchText) ||
                            user.email.lowercase().contains(searchText) ||
                            user.birthday.lowercase().contains(searchText) ||
                            user.address.lowercase().contains(searchText)
                }

                Toast.makeText(
                    this,
                    if (found) "User Found!" else "No User Found!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Please add a user first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
