import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 2.
    val input = Files.readString(Paths.get("day-02/input.txt"))

    val ranges: List<Range> = input.split(",").map { it.toRange() }
    val invalidIds = mutableListOf<Long>()

    ranges.forEach {
        for (i in it.start..it.end) {
            val str = i.toString()

            // Find all the numbers starting with 0
            if (str.startsWith("0")) {
                invalidIds.add(i)
                continue
            }

            // Find all the numbers whose first half and second half are identical
            /** For the first part of the challenge
            val (left, right) = str.splitInHalf()
            if (left == right) {
                invalidIds.add(i)
            }
            **/

            var chunkLength = 1
            while (chunkLength <= str.length / 2) {
                // Extend a portion of digits from the start of the number from 1 to a half of number digits
                // for each match iteration.
                val chunk = str.take(chunkLength)

                // skip a chunk if its length is not a proper divisor of the length of a string representing an ID number
                if (str.length % chunk.length != 0) {
                    chunkLength++
                    continue
                }

                // if a chunk of digits repeats in a number multiple times, this is an invalid ID number
                val regex = Regex("($chunk){${str.length / chunk.length}}")
                if (regex.matches(str)) {
                    invalidIds.add(i)
                    break
                }

                // Extend a chunk for the next search iteration of repeating subsequences in a number
                chunkLength++
            }
        }
    }

    val result = invalidIds.sum()
    println("Result: $result")
}

data class Range(
    val start: Long,
    val end: Long
)

private fun String.splitInHalf(): Pair<String, String> {
    val mid = length / 2
    return substring(0, mid) to substring(mid)
}

private fun String.toRange(): Range {
    val parsed = split("-")
    return Range(parsed[0].toLong(), parsed[1].toLong())
}