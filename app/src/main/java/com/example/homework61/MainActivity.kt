package com.example.homework61

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homework61.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUP()

    }
    private fun setUP(){
        binding.textOfPlace.text = "Andes Mountains"
        binding.locationTextView.text = "South,America"
        binding.priceNumberTextView.text = "﹩220"
        binding.smallTextTimeTextView.text = "8 Hours"
        binding.smallTextTemperatureTextView.text = "16 °C"
        binding.smallTextRatingTextView.text = "4.5"
        binding.bigReviewText.text= "This vast mountain range is renowned for its remarkable diversity in terms of topography and climate. It features towering peaks, active volcanoes, deep canyons, expansive plateaus, and lush valleys. The Andes are also home to "

    }
}
