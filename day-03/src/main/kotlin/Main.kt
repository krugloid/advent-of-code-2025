import java.io.File

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 3.
    val lines = File("day-03/input.txt").readLines()

    // Part 1
    val maxNumbers: List<Int> = lines.map { number ->
        val listOfDigits: List<Int> = number.toList().map { it.toString().toInt() }
        val sortedDigits: List<Int> = listOfDigits.sortedDescending()

        // Find the biggest number in a sorted by descend list of digits or the second biggest if the first one
        // is at the very end of the list
        var indexOfBiggestNumber = listOfDigits.indexOfFirst { it == sortedDigits[0] }
        if (indexOfBiggestNumber == listOfDigits.size - 1) {
            indexOfBiggestNumber = listOfDigits.indexOfFirst { it == sortedDigits[1] }
        }

        // Fetch all the digits in the number that go after the biggest (or second biggest) digit
        // and find a maximum number in that sublist
        val sublist = listOfDigits.subList(indexOfBiggestNumber+1, listOfDigits.size)
        "${listOfDigits[indexOfBiggestNumber]}${sublist.max()}".toInt()
    }

    println("Result: ${maxNumbers.sum()}")

    // Part 2
    val maxNumbers2: List<Long> = lines.map { number ->
        var newNumber = number

        while (newNumber.length > 12) {
            for (i in 0..<newNumber.length) {
                // When a number has identical digits in its trailing, trim the number string by the target length 12
                if (i + 1 == newNumber.length) {
                    newNumber = newNumber.take(12)
                    break
                }
                // Remove the first digit that is smaller than the next one
                if (newNumber[i].toString().toInt() <= newNumber[i+1].toString().toInt()) {
                    newNumber = newNumber.replaceFirst(newNumber[i].toString(), "")
                    break
                }
            }
        }
        newNumber.toLong()
    }

    println("Result: ${maxNumbers2.sum()}")
}
