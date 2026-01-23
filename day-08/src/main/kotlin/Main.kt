import java.io.File
import kotlin.math.sqrt

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 8.
    val lines = File("day-08/input.txt").readLines()

    val nodes = lines.map {
        val (x, y, z) = it.split(",").map { coordinate ->
            coordinate.toLong()
        }
        Triple(x, y, z)
    }

    val distances = nodes.flatMapIndexed { from, node1 ->
        nodes.mapIndexedNotNull { to, node2 ->
            if (to <= from) {
                null
            } else {
                // calc distance here
                val dist = sqrt(sqr(node2.first - node1.first).toDouble() +
                        sqr(node2.second - node1.second).toDouble() +
                        sqr(node2.third - node1.third).toDouble())
                Pair(from, to) to dist
            }
        }
    }.sortedBy { it.second }

    val circuits = mutableListOf(mutableListOf<Int>())
    var part2Result = 0L
    circuits.removeIf { it.isEmpty() }
    /**
     * Uncomment for Part 1 instead of line 37.
     * distances.take(1000).forEach { chunk ->
     */
    distances.forEach { chunk ->
        /**
         * -------------- Comment for Part 1. -------------------
         */
        if (circuits.size == 1 && circuits.first().size == nodes.size - 1) {
            part2Result = nodes[chunk.first.first].first * nodes[chunk.first.second].first
        }
        /** ----------------------------------------------------- */
        if (circuits.none { it.contains(chunk.first.first) } && circuits.none { it.contains(chunk.first.second) } ) {
            circuits.add(mutableListOf(chunk.first.first, chunk.first.second))
        } else {
            val mergedCircuits = mutableListOf<Int>()
            circuits.filter { it.contains(chunk.first.first) || it.contains(chunk.first.second) }.forEach { circuit ->
                mergedCircuits.addAll(circuit)
            }
            circuits.removeIf { it.contains(chunk.first.first) || it.contains(chunk.first.second) }

            if (mergedCircuits.none { it == chunk.first.first }) {
                mergedCircuits.add(chunk.first.first)
            }
            if (mergedCircuits.none { it == chunk.first.second }) {
                mergedCircuits.add(chunk.first.second)
            }
            circuits.add(mergedCircuits)
        }
    }

    val result = circuits.sortedByDescending { it.size }.take(3).map { it.size }.reduce { acc, n -> acc * n }
    println("=== Result: $result")
    println("=== Result2: $part2Result")
}

fun sqr(base: Long): Long {
    var result = 1L
    repeat(2) { result *= base }
    return result
}
