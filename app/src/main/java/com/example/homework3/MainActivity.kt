package com.example.homework3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
     val numberGeoWords = mapOf(
        1 to "ერთი", 2 to "ორი", 3 to "სამი", 4 to "ოთხი", 5 to "ხუთი",
        6 to "ექვსი", 7 to "შვიდი", 8 to "რვა", 9 to "ცხრა", 10 to "ათი",
        11 to "თერთმეტი", 12 to "თორმეტი", 13 to "ცამეტი", 14 to "თოთხმეტი",
        15 to "თხუთმეტი", 16 to "თექვსმეტი", 17 to "ჩვიდმეტი", 18 to "თვრამეტი",
        19 to "ცხრამეტი", 20 to "ოცი", 30 to "ოცდაათი", 40 to "ორმოცი",
        50 to "ორმოცდაათი", 60 to "სამოცი", 70 to "სამოცდაათი",
        80 to "ოთხმოცი", 90 to "ოთხმოცდაათი", 100 to "ას", 200 to "ორას",
        300 to "სამას", 400 to "ოთხას", 500 to "ხუთას", 600 to "ექვსას",
        700 to "შვიდას", 800 to "რვაას", 900 to "ცხრაას", 1000 to "ათასი"
    )

    val numberEnglishWords = mapOf(
        1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five",
        6 to "six", 7 to "seven", 8 to "eight", 9 to "nine", 10 to "ten",
        11 to "eleven", 12 to "twelve", 13 to "thirteen", 14 to "fourteen",
        15 to "fifteen", 16 to "sixteen", 17 to "seventeen", 18 to "eighteen",
        19 to "nineteen", 20 to "twenty", 30 to "thirty", 40 to "forty",
        50 to "fifty", 60 to "sixty", 70 to "seventy", 80 to "eighty",
        90 to "ninety", 100 to "hundred", 200 to "two hundred", 300 to "three hundred",
        400 to "four hundred", 500 to "five hundred", 600 to "six hundred",
        700 to "seven hundred", 800 to "eight hundred", 900 to "nine hundred",
        1000 to "thousand"
    )

    //421 % 10 = 1 = lastDigit, 421/10 = 42 = newNumber,  42  % 10 = 2 = decimalNumber , 42/10 = 4 = hundredNumber,
    fun numberToGeoText(number: Int): String {
        val lastDigit = number % 10
        val newNumber = number / 10
        val decimalNumber = newNumber % 10
        val hundredNumber = newNumber / 10
        val lastTwoDigits = decimalNumber * 10 + lastDigit

        var result = ""

        // 100+
        if (hundredNumber >= 1) {
            // IF number is exactly 100 , 200, 300 and so on up to 900
            if (lastTwoDigits == 0){
                result += numberGeoWords[number] + "ი"
            } else{
                result += numberGeoWords[hundredNumber * 100]
            }

        }

        // second and third numbers
        if (lastTwoDigits > 0) {
            // 01-09
            if (lastTwoDigits >=1 && lastTwoDigits <=9){
                result += numberGeoWords[lastDigit]
            }
            // 11-19
            else if (lastTwoDigits >= 11 && lastTwoDigits <= 19) {
                result += numberGeoWords[lastTwoDigits]
            }
            // 21-29
            else if (lastTwoDigits >= 21 && lastTwoDigits <= 29) {
                result += "ოცდა" + numberGeoWords[lastDigit]
            }
            // 31-39
            else if (lastTwoDigits >= 31 && lastTwoDigits <= 39) {
                result += "ოცდა" + numberGeoWords[lastTwoDigits - 20]
            }
            // 41-49
            else if (lastTwoDigits >= 41 && lastTwoDigits <= 49) {
                result += "ორმოცდა" + numberGeoWords[lastDigit]
            }
            // 51-59
            else if (lastTwoDigits >= 51 && lastTwoDigits <= 59) {
                result += "ორმოცდა" + numberGeoWords[lastTwoDigits - 40]
            }
            // 61-69
            else if (lastTwoDigits >= 61 && lastTwoDigits <= 69) {
                result += "სამოცდა" + numberGeoWords[lastDigit]
            }
            // 71-79
            else if (lastTwoDigits >= 71 && lastTwoDigits <= 79) {
                result += "სამოცდა" + numberGeoWords[lastTwoDigits - 60]
            }
            // 81-89
            else if (lastTwoDigits >= 81 && lastTwoDigits <= 89) {
                result += "ოთხმოცდა" + numberGeoWords[lastDigit]
            }
            // 91-99
            else if (lastTwoDigits >= 91 &&  lastTwoDigits<= 99) {
                result += "ოთხმოცდა" + numberGeoWords[lastTwoDigits - 80]
            }

            else {
                if (decimalNumber > 0) {
                    result += numberGeoWords[decimalNumber * 10]
                }
            }
        }
        return result
    }

    fun numberToEnglishText(number: Int): String {
        val lastDigit = number % 10
        val newNumber = number / 10
        val decimalNumber = newNumber % 10
        val hundredNumber = newNumber / 10
        val lastTwoDigits = decimalNumber * 10 + lastDigit

        var result = ""

        if (hundredNumber >= 1) {
            result += numberEnglishWords[hundredNumber * 100] ?: ""
        }

        if (lastTwoDigits >= 11 && lastTwoDigits <= 19) {
            result += numberEnglishWords[lastTwoDigits] ?: ""
        } else {
            if (decimalNumber >= 1) {
                result += numberEnglishWords[decimalNumber * 10] ?: ""
            }
            if (lastDigit >= 1) {
                result += numberEnglishWords[lastDigit] ?: ""
            }
        }

        return result
    }

    lateinit var editTextInput: EditText
    lateinit var button: Button
    lateinit var textView: TextView
    lateinit var languageChangeToggle: ToggleButton

    var isGeorgian = true

    fun changeText() {
        if (isGeorgian) {
            button.text = "დააკლიკე რომ დაიწეროს სიტყვით"
            textView.text = "შეიყვანე რიცხვი 1 დან 1000 მდე"
            editTextInput.hint = "შეიყვანეთ ნომერი აქ"
        } else {
            button.text = "Click to write with words"
            textView.text = "Write number from 1 to 1000"
            editTextInput.hint = "Enter number here"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextInput = findViewById(R.id.editTextInput)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
        languageChangeToggle = findViewById(R.id.languageChangeToggle)

        languageChangeToggle.isChecked = true
        changeText()

        languageChangeToggle.setOnCheckedChangeListener{ _, isChecked ->
            isGeorgian = isChecked
            changeText()
        }
        button.setOnClickListener{

            var userInput = editTextInput.text.toString()
            var number = userInput.toInt()
            val finalGeoWord = numberToGeoText(number)
            val finalEnglishWord = numberToEnglishText(number)

            if (isGeorgian){
                textView.text = "შედეგი: $finalGeoWord"
            } else {
                textView.text = "Result: $finalEnglishWord"
            }
        }
    }
}