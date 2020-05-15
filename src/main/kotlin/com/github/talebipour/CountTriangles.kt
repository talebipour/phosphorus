package com.github.talebipour

object CountTriangles {

    fun solve(a: Long, b: Long, c: Long, d: Long) : Long {
        var count = 0L
        for (x in a..b) {
            val border = maxOf(d - x + 1, b)
            count += maxOf(c - border + 1, 0) * (d - c + 1).toLong()
            val serieStart = x + maxOf(c - x + 1, b) - c
            val serieEnd = x + minOf(border - 1, c) - c
            count += (serieEnd + serieStart) * (serieEnd - serieStart + 1)  / 2
        }
        return count
    }
}

fun main() {
    val input = readLine()!!.split(" ")
    val result = CountTriangles.solve(input[0].toLong(), input[1].toLong(), input[2].toLong(), input[3].toLong())
    println(result)
}