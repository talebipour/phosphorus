package com.github.talebipour

import java.io.BufferedWriter
import java.io.OutputStreamWriter

fun main() {
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val t = readLine()!!.toInt()
    for (i in 1..t) {
        val nkd = readLine()!!.split(" ")
        val schedule = readLine()!!.split(" ").stream().mapToInt(String::toInt).toArray()
        writer.appendln(solve(nkd[0].toInt(), nkd[2].toInt(), schedule).toString())
    }
    writer.flush()
}

fun solve(n: Int, d: Int, schedule: IntArray) : Int {
    val showCount = HashMap<Int, Int>()
    var maxDistinctShows = d
    for (i in 0 until n) {
        if (i >= d) {
            val removedShow = schedule[i - d]
            showCount[removedShow] = showCount[removedShow]!! - 1
            if (showCount[removedShow] == 0) {
                showCount.remove(removedShow)
            }
        }
        showCount.merge(schedule[i], 1, Int::plus)
        if (i >= d - 1) {
            maxDistinctShows = minOf(maxDistinctShows, showCount.size)
        }
    }
    return maxDistinctShows
}