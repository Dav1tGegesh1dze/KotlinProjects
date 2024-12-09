package com.example.test1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    private fun setUp() {
        val inputForAnagrams = binding.editTextAnagram
        val saveTextButton = binding.saveTextButton
        val outputButton = binding.saveOutputButton
        val outputWithText = binding.textViewOutput
        val clearButton = binding.clearButton
        val bleButton = binding.bleButton

        val savedStrings = mutableListOf<String>()

        bleButton.setOnClickListener{
            // khumrobitaa, ar chamitvalot realobashi
            outputWithText.text = "HELLO I'M hacker from tbc academy"
        }
        saveTextButton.setOnClickListener {
            val inputText = inputForAnagrams.text.toString()
            if (inputText.isNotEmpty()) {
                savedStrings.add(inputText)
            } else {
                outputWithText.text = "ENter text"
            }
        }

        clearButton.setOnClickListener {
            savedStrings.clear()
            outputWithText.text = ""
        }


        outputButton.setOnClickListener {
            val hasWords = savedStrings.isNotEmpty()


            if (!hasWords) {
                outputWithText.text = "Nothing saved yet"
            } else {
                val groupedAnagrams = groupAnagrams(savedStrings)


                val filteredGroups = mutableListOf<List<String>>()

                for (group in groupedAnagrams) {
                    if (group.size > 1) {
                        filteredGroups.add(group)
                    }
                }

                if (filteredGroups.isEmpty()) {
                    outputWithText.text = "No anagram "
                } else {
                    val formattedOutput = filteredGroups.joinToString("\n") { group ->
                        group.joinToString(", ")
                    }
                    outputWithText.text = formattedOutput
                }
            }
        }


    }


    private fun groupAnagrams(words: List<String>): List<List<String>> {
        val result = mutableListOf<MutableList<String>>()

        for (word in words) {
            var foundGroup = false

            for (group in result) {
                val firstWord = group[0]
                if (word.toList().sorted().joinToString("") == firstWord.toList().sorted().joinToString("")) {
                    group.add(word)
                    foundGroup = true
                    break
                }
            }

            if (!foundGroup) {
                result.add(mutableListOf(word))
            }
        }

        return result
    }
}