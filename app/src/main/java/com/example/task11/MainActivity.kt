package com.example.task11


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.show_info_fragment, showInfoFragment())
                .commit()
        }
    }
    //go to addfragment
    fun navigateToAddInfoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.add_fragment, AddInfoFragment())
            .addToBackStack(null) // Add transaction to back stack
            .commit()
    }

    fun navigateBackToShowInfoFragment() {
        supportFragmentManager.popBackStack() // Return to previous fragment
    }
}
