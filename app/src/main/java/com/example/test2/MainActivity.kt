package com.example.test2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setUpNextButton()
        setUpSaveButton()
        setContentView(binding.root)

    }

    private fun setUpSaveButton(){
        binding.saveButton.setOnClickListener{
            getData()
        }
    }

    private fun getData(){
        val inputText = binding.firstEditText.text.toString().trim()
        if (inputText.isNotEmpty()) {
            var matchFound = false
            //check if equals
            for (user in viewModel.getUsers()) {
                if (user.firstName.equals(inputText, ignoreCase = true) ||
                    user.lastName.equals(inputText, ignoreCase = true)  ||
                    user.birthday.equals(inputText, ignoreCase = true)  ||
                    user.address.equals(inputText, ignoreCase = true)  ||
                    user.email.equals(inputText, ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        "Match found: ${user.firstName} ${user.lastName} (Birthday: ${user.birthday}  (ID: ${user.id})",
                        Toast.LENGTH_SHORT
                    ).show()
                    matchFound = true
                    break
                }
            }

            if (!matchFound) {
                Toast.makeText(this, "No match found.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter a name to check.", Toast.LENGTH_SHORT).show()
        }

    }


    private fun setUpNextButton() {
        binding.nextButton.setOnClickListener {
            binding.firstEditText.visibility = View.GONE
            binding.nextButton.visibility = View.GONE
            binding.saveButton.visibility = View.GONE
            addFragment()
        }
    }

    private fun addFragment() {
        val supportManager = supportFragmentManager
        val transaction = supportManager.beginTransaction()
        transaction.replace(R.id.fragment_container, AddUserFragment(), "fragment")

        transaction.addToBackStack("fragment")
        transaction.commit()
    }

    // Handle back press to show main activity views again
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            binding.firstEditText.visibility = View.VISIBLE
            binding.nextButton.visibility = View.VISIBLE
            binding.saveButton.visibility = View.VISIBLE
            super.onBackPressed()
        } else {
            super.onBackPressed()
        }


    }
}