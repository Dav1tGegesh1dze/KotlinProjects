package com.example.task9

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task9.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
        setUp2()
    }

    private fun handleFragmentBackStack() {
        // If no fragments in the back stack, show the views
        if (supportFragmentManager.backStackEntryCount == 0) {
            showViews()
        }
    }
    private fun setUp(){
        binding.signUpTextFirstPage.setOnClickListener{

            addFragment1()
        }
        //someone changes backStack fragment
        supportFragmentManager.addOnBackStackChangedListener {
            handleFragmentBackStack()
        }
    }

    private fun setUp2(){

        binding.signInButton.setOnClickListener() {
            addFragment2()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            handleFragmentBackStack()
        }
    }

    private fun addFragment2(){
        val supportManager = supportFragmentManager
        val transaction = supportManager.beginTransaction()
        transaction.replace(R.id.fragment_container, SecondLogInFragment(), "second_fragment_container")

        transaction.addToBackStack("second_fragment_container")
        transaction.commit()
        hideViews()
    }

    private fun addFragment1(){
        val supportManager = supportFragmentManager
        val transaction = supportManager.beginTransaction()
        transaction.replace(R.id.fragment_container,FirstSignWithPasswordFragment(), "fragment_container")


        transaction.addToBackStack("fragment_container")

        transaction.commit()
        hideViews()

    }

    private fun hideViews(){
        binding.titleTextMain.visibility = View.GONE
        binding.signInTextLinearLayout.visibility = View.GONE
        binding.topPhoto.visibility = View.GONE
        binding.signInButton.visibility = View.GONE
        binding.facebookButton.visibility = View.GONE
        binding.googleButton.visibility = View.GONE
        binding.applePhoto.visibility = View.GONE
        binding.orText.visibility = View.GONE
    }

    private fun showViews(){
        binding.titleTextMain.visibility = View.VISIBLE
        binding.signInTextLinearLayout.visibility = View.VISIBLE
        binding.topPhoto.visibility = View.VISIBLE
        binding.signInButton.visibility = View.VISIBLE
        binding.facebookButton.visibility = View.VISIBLE
        binding.googleButton.visibility = View.VISIBLE
        binding.applePhoto.visibility = View.VISIBLE
        binding.orText.visibility = View.VISIBLE
    }


}