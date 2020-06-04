package com.github.talebipour

import java.util.*


fun main() {
    val n = readLine()!!.toInt()
    val garland = readLine()!!.split(" ").stream().mapToInt(String::toInt).toArray()

    val missingOds = mutableSetOf<Int>()
    val missingEvens = mutableSetOf<Int>()
    for (i in 1..n) {
        (if (i % 2 == 0) missingEvens else missingOds).add(i)
    }

    val missingRanges = TreeSet<Triple</* start */ Int, /* end */Int, /* score */Int>> {
        o1, o2 -> val scoreCompare = (o1.third).compareTo(o2.third)
        if (scoreCompare == 0) o1.first.compareTo(o2.first) else scoreCompare
    }
    var missingStart = -1
    for (i in 0 until n) {
        if (garland[i] == 0) {
            if (missingStart < 0) {
                missingStart = i
            }
        } else {
            (if (garland[i] % 2 == 0) missingEvens else missingOds).remove(garland[i])
            if (missingStart >= 0) {
                missingRanges.add(createRange(garland, missingStart, i))
                missingStart = -1
            }
        }
    }
    if (missingStart >= 0) {
        missingRanges.add(createRange(garland, missingStart, n))
    }

    var range = missingRanges.pollFirst()
    while (range != null) {
        val even = garland[if (range.first == 0) minOf(range.second, n - 1) else range.first - 1] % 2 == 0
        val firstCandidates = (if (even) missingEvens else missingOds)
        val secondCandidates = (if (even) missingOds else missingEvens)
        if (range.third < 100 && firstCandidates.size < (range.second - range.first)) {
            missingRanges.add(Triple(range.first, range.second, range.third + 200))
        } else {
            fillRange(range, firstCandidates, secondCandidates, garland)
        }
        range = missingRanges.pollFirst()
    }

    // Calculate complexity
    var complexity = 0
    for (i in 1 until n) {
        if (garland[i] % 2 != garland[i - 1] % 2) {
            complexity++
        }
    }
//    println(garland.contentToString())
    println(complexity)
}

private fun fillRange(range: Triple<Int, Int, Int>, firstCandidates: MutableSet<Int>, secondCandidates: MutableSet<Int>,
                      garland: IntArray) {
    val firstCandidatesIterator = firstCandidates.iterator()
    val secondCandidatesIterator = secondCandidates.iterator()
    val fillRange = if (range.first == 0) range.second - 1 downTo 0 else range.first until range.second
    for (i in fillRange) {
        if (firstCandidatesIterator.hasNext()) {
            garland[i] = firstCandidatesIterator.next()
            firstCandidatesIterator.remove()
        } else {
            garland[i] = secondCandidatesIterator.next()
            secondCandidatesIterator.remove()
        }
    }
}

fun createRange(garland: IntArray, missingStart: Int, missingEnd: Int) : Triple<Int, Int, Int> {
    val score = when {
        missingStart == 0 || missingEnd == garland.size -> {
            100
        }
        garland[missingStart - 1] % 2 == garland[missingEnd] % 2 -> {
            0
        }
        else -> {
            200
        }
    } + (missingEnd - missingStart)
    return Triple(missingStart, missingEnd, score)
}