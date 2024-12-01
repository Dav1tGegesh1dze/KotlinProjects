class MathFunctions {
    //Usg -1
    fun returnUsg(number1: Int, number2: Int): Int {
        var min = minOf(number1, number2)
        while (min != 0) {
            if (number1 % min == 0 && number2 % min == 0) {
                return min
            }
            min--
        }
        //If there's no USG answer is 1
        return 1
    }

    //USJ -2
    fun returnUsj(number1: Int, number2: Int): Int {
        var max = maxOf(number1, number2)
        while (true) {
            if (max % number1 == 0 && max % number2 == 0) {
                return max

            }
            max++
        }
    }

    //contains $ -3
    fun containsSymbol(text: String): Boolean {
        for (i in text.indices) {
            if (text[i] == '$') {
                return true
            }
        }
        return false
    }

    //recursive sum up to 100 -4
    fun recSum(num: Int = 100): Int {
        if (num < 1) {
            return num
        }
        return num + recSum(num - 1)

    }

    //reverse number -5
    fun reverseNum(num: Int): Int {
        val numasStr = num.toString()
        val newStr = numasStr.reversed()
        return newStr.toInt()

    }

    //reverse number -5.1 (Second way)
    fun reversedNum1(num: Int): Int {
        var reversedNum = 0
        var mainNum = num
        while (mainNum > 0) {
            val newNum = mainNum % 10
            reversedNum = reversedNum * 10 + newNum
            mainNum /= 10
        }
        return reversedNum
    }

    //Check palindrome -6
    fun checkPalindrome(text: String): Boolean {
       return text == text.reversed()
    }

    //Check palindrome -6.1
    fun checkPalindrome1(text: String): Boolean {
        for (i in 0..<text.length -1) {
            if (text[i] != text[text.length - i - 1]) {
                return false
            }

        }
        return true
    }

}



    fun main() {
        val myMathFunctions = MathFunctions()
        println(myMathFunctions.returnUsg(100,15))
        println(myMathFunctions.returnUsj(14,28))
        println(myMathFunctions.containsSymbol("WhatSymbol$"))
        println(myMathFunctions.recSum())
        println(myMathFunctions.reverseNum(1300))
        println(myMathFunctions.reversedNum1(1230))
        println(myMathFunctions.checkPalindrome("radar"))
        println(myMathFunctions.checkPalindrome1("radarr"))
    }
