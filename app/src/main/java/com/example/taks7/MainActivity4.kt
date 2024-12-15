package com.example.taks7

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taks7.databinding.ActivityMain4Binding
import com.example.taks7.databinding.ActivityMainBinding

class MainActivity4 : AppCompatActivity() {
    private lateinit var binding:ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setupButtons()

    }

    private fun setupButtons(){
        binding.backIcon3.setOnClickListener{
            finish()
        }

        binding.signUpButton.setOnClickListener{
            finishRegistration()
        }

    }

   private fun finishRegistration(){
       if(binding.usernameEditText.text.toString().isNotEmpty()){
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
       } else{
           binding.usernameErrorTextView.text = "Write Username correctly"
       }
   }

}