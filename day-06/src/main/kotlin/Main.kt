import java.io.File

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 6.
    val lines = File("day-06/input.txt").readLines()

    val problemCount = lines[0].trim().split(Regex("\\s+")).size
    // Parse problem input numbers in columns delimited by space(s)
    var problemInputs = Array(problemCount) { mutableListOf<Long>() }
    lines.take(lines.size - 1).forEach { line ->
        line.trim().split(Regex("\\s+")).mapIndexed { index, num -> problemInputs[index].add(num.toLong()) }
    }

    // Parse operators for each problem specified in the last line
    val operators = lines.last().trim().split(Regex("\\s+"))

    // evaluate the result of arithmetical operations linked to each problem and sum up those results
    var result = 0L
    problemInputs.forEachIndexed { index, input ->
        result += if (operators[index] == "+") {
            input.sum()
        } else {
            input.reduce { accumulator, value -> accumulator * value }
        }
    }
    println("Result for part 1: $result")

    // ---- Part 2. ---- //
    // add trailing spaces to the lines of the input file so that all of them match the longest line
    val maxLineLength = lines.maxOf { it.length }
    val paddedLines = lines.map { it.padEnd(maxLineLength, ' ') }

    problemInputs = Array(problemCount) { mutableListOf() }
    var problemPointer = 0
    for (col in (paddedLines[0].length - 1) downTo 0) {
        var number = ""
        for (row in 0..<paddedLines.size - 1) {
            if (paddedLines[row][col] == ' ') {
                // skip no-digit position
            } else {
                // add a new digit to the current number
                number += paddedLines[row][col]
            }
        }
        if (number.trim().isNotEmpty()) {
            // add accumulated number to a list of problem numbers
            problemInputs[problemPointer].add(number.toLong())
        }
        if (paddedLines.last()[col] == '+' || paddedLines.last()[col] == '*') {
            // Switch to accumulating numbers for the next problem
            problemPointer++
        }
    }

    // Reverse collected problem inputs and evaluate the result of arithmetical operations linked to each problem
    problemInputs.reverse()
    problemInputs.forEachIndexed { index, input ->
        result += if (operators[index] == "+") {
            input.sum()
        } else {
            input.reduce { accumulator, value -> accumulator * value }
        }
    }

    println("Result for part 2: $result")
}
