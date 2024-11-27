fun main() {
    var answer = "Y"
    while (answer == "Y" || answer == "y") {
        //X Logic
        println("Enter X:")
        val x = readLine() ?: ""
        var numbersOnlyX = ""
        for (char in x) {
            if (char in '0'..'9') {
                numbersOnlyX += char
            }
        }
        var numberX = 0
        if (numbersOnlyX.isNotEmpty()) {
            numberX = numbersOnlyX.toInt()
        }
        println(numberX)

        //Y Logic
        var numberY = 0
        while (numberY == 0) {
            println("Enter Y:")

            val y = readLine() ?: ""
            var numbersOnlyY = ""
            for (char in y) {
                if (char in '0'..'9') {
                    numbersOnlyY += char
                }
            }
            if (numbersOnlyY.isNotEmpty()) {
                numberY = numbersOnlyY.toInt()
            }

            if (numberY == 0) {
                println("Y can't be zero, There is no divided by 0")
            }
        }
        println("Y is : $numberY")

        val Z = numberX/numberY
        println("Z is X divided by Y and equals : $Z")

        println("Do you wanna continue? Y/N")
        answer = readLine().toString()
    }
    println("დასასრული! ! !")
}