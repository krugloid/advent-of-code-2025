import java.io.File

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 5.
    val lines = File("day-05/input.txt").readLines()

    val ranges = lines.subList(0, lines.indexOfFirst { it.isEmpty() }).map { range ->
        val parts = range.split("-", limit = 2)
        parts[0].toLong()..parts[1].toLong()
    }
    val ids = lines.subList(lines.indexOfFirst { it.isEmpty() } + 1, lines.size).map { it.toLong() }

    var result = 0
    ids.forEach { id ->
        if (ranges.firstOrNull { id in it.first..it.last } != null) {
            result++
        }
    }

    // ---- Part 2. ---- //
    val mergedRanges = mergeRanges(ranges)
    val totalRangeCount = mergedRanges.sumOf { it.last - it.first + 1 }
    println("=== Result: $result. Total list of fresh ids: $totalRangeCount")
}

fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
    if (ranges.isEmpty()) return emptyList()

    val sorted = ranges.sortedBy { it.first }
    val result = mutableListOf(sorted.first())

    for (current in sorted.drop(1)) {
        val last = result.last()
        if (current.first <= last.last + 1) {
            result[result.lastIndex] =
                last.first..maxOf(last.last, current.last)
        } else {
            result.add(current)
        }
    }
    return result
}
