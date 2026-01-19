import java.io.File

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 4.
    val lines = File("day-04/input.txt").readLines()

    var grid = linesToGrid(lines)

    var total = 0
    while (true) {
        val (removedRollCount, newGrid) = removeAndCountRolls(grid)
        total += removedRollCount
        grid = newGrid

        if (removedRollCount == 0) break
    }

    println("Result: $total")
}

private fun removeAndCountRolls(grid: Array<BooleanArray>): Pair<Int, Array<BooleanArray>> {
    var adjacentRollCnt = 0
    var result = 0
    val positionsToRemove = mutableListOf<Pair<Int, Int>>()
    for (i in 0..<grid.size) {
        for (j in 0..<grid[0].size) {
            // Skip a position if it's empty (i.e. not a roll)
            if (!grid[i][j]) {
                continue
            }

            // Count all the adjacent positions with a roll in there for the current cell in the grid
            try {
                adjacentRollCnt += if (grid[i - 1][j - 1]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }
            try {
                adjacentRollCnt += if (grid[i - 1][j]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }
            try {
                adjacentRollCnt += if (grid[i - 1][j + 1]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }
            try {
                adjacentRollCnt += if (grid[i][j - 1]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }
            try {
                adjacentRollCnt += if (grid[i][j + 1]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }
            try {
                adjacentRollCnt += if (grid[i + 1][j - 1]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }
            try {
                adjacentRollCnt += if (grid[i + 1][j]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }
            try {
                adjacentRollCnt += if (grid[i + 1][j + 1]) 1 else 0
            } catch (_: IndexOutOfBoundsException) { }

            // If there are not more than 3 rolls adjacent, count this position as valid
            if (adjacentRollCnt < 4) {
                // Cache the positions where the rolls can be removed
                positionsToRemove.add(Pair(i, j))
                // Increase the count of to-be-removed rolls
                result++
            }
            adjacentRollCnt = 0
        }
    }

    // Remove the rolls that were identified as the ones that can be removed
    positionsToRemove.forEach {
        grid[it.first][it.second] = false
    }
    return result to grid
}

private fun printGrid(grid: Array<BooleanArray>) {
    val builder = StringBuilder()
    for (i in 0..<grid.size) {
        for (j in 0..<grid[0].size) {
            builder.append(if (grid[i][j]) '@' else '.')
        }
        println(builder.toString())
        builder.clear()
    }
}

private fun linesToGrid(lines: List<String>): Array<BooleanArray> {
    val grid = Array(lines[0].length) { BooleanArray(lines.size) }
    // Transform input into a grid of booleans where true stands for a presence of a roll in that position
    // and false - for the absence of a roll.
    lines.forEachIndexed { i, line ->
        line.toList().forEachIndexed { j,  item ->
            grid[i][j] = (item == '@')
        }
    }
    return grid
}