import java.io.File
import kotlin.math.floor

fun main() {
    // Input for a challenge cannot be added to this repo as it is covered by Copyright 2015-2025 Advent of Code.
    // It can be found at https://adventofcode.com/2025 for day 1.
    val lines = File("day-01/input.txt").readLines()

    var pointer = 50
    var counter = 0
    lines.forEach {
        val rotation = it.toRotation()
        if (rotation is Rotation.Right) {
            counter += floor((pointer + rotation.number) / 100f).toInt()
            pointer = (pointer + rotation.number) % 100
        }
        if (rotation is Rotation.Left) {
            counter += if (pointer > rotation.number % 100) {
                floor(rotation.number / 100f).toInt()
            } else {
                floor(rotation.number / 100f).toInt() + if (pointer == 0) 0 else 1
            }
            pointer = (pointer - (rotation.number % 100) + 100) % 100
        }
    }

    println("Password: $counter")
}

private fun String.toRotation() = if (startsWith("L")) {
    Rotation.Left(substring(1).toInt())
} else {
    Rotation.Right(substring(1).toInt())
}

sealed class Rotation(val number: Int) {
    class Left(i: Int): Rotation(i)
    class Right(i: Int): Rotation(i)
}
