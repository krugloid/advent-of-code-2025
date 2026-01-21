import java.io.File

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 7.
    val linesOrig = File("day-07/input.txt").readLines()
    val lines = linesOrig.drop(1)

    var splitCount = 0  // part 1 result
    var timelineCount = 0L  // part 2 result

    val splitCountGrid = Array(lines.size) { LongArray(lines[0].length) }
    splitCountGrid[0][linesOrig.first().indexOfFirst { it == 'S' }] = 1

    for (row in 0 until lines.size) {
        var line = lines[row]

        splitCountGrid[row].forEachIndexed { col, item ->
            if (item > 0) {
                line = line.replaceRange(col, col+ 1, "|")
            }
            if (row + 1 == lines.size) {
                return@forEachIndexed
            }

            if (lines[row + 1][col] == '^') {
                // TODO check horizontal boundaries
                splitCountGrid[row + 1][col - 1] += item
                splitCountGrid[row + 1][col + 1] += item
                if (line[col] == '|') {
                    splitCount++
                }
            } else if (line[col] == '|') {
                splitCountGrid[row + 1][col] += item
            }
        }
    }

    timelineCount += splitCountGrid[lines.size - 1].sum()

    println("=== Result: $splitCount $timelineCount")
}