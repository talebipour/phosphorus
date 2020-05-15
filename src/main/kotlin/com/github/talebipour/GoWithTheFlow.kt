package com.github.talebipour

import java.io.ByteArrayInputStream
import java.util.*

fun main() {
    val input = generateInput(2500)
    System.setIn(ByteArrayInputStream(input.toByteArray()))
    val wordCount = readLine()!!.toInt()
    val words = readWords(wordCount) {
        readLine()!!
    }
    solve(words)
}

private fun generateInput(wordCount: Int): String {
    val inputBuilder = StringBuilder()
    inputBuilder.append(wordCount).append("\n")
    var i = 0
    while (i < wordCount) {
        for (i in 1..minOf(80, wordCount - i - 1)) {
            inputBuilder.append("a ")
        }
        inputBuilder.append("a".repeat(80)).append(" ")
        i += 81
    }
    inputBuilder.deleteCharAt(inputBuilder.length - 1)
    return inputBuilder.toString()
}

private fun solve(words: MutableList<Byte>) {
    var lineWidth = words.max()!!.toInt()
    val maxWidth = words.sum() + words.size - 1
    var longestRiverLength = 0
    var longestRiverLineWidth = lineWidth
    while (lineWidth <= maxWidth) {
        val alignment = align(lineWidth, words)
        val currentRiverLength = findLongestRiver(alignment)
        if (currentRiverLength > longestRiverLength) {
            longestRiverLineWidth = lineWidth
            longestRiverLength = currentRiverLength
        }

        if (alignment.size < longestRiverLength) {
            break
        }
        lineWidth++
    }
    print("$longestRiverLineWidth $longestRiverLength")
    println("Max line width tried: $lineWidth")
}

fun align(lineWidth: Int, wordLengths: MutableList<Byte>) : List<Line> {
    val alignment = mutableListOf<Line>()
    var currentLine = Line(lineWidth)
    alignment.add(currentLine)
    var cursor = 0
    for (wordLength in wordLengths) {
        if (cursor + wordLength > lineWidth) {
            if (cursor < currentLine.lineWidth) {
                Arrays.fill(currentLine.charAlignment, cursor, currentLine.lineWidth, 0)
            }
            currentLine = Line(lineWidth)
            alignment.add(currentLine)
            cursor = 0
        }
        if (cursor > 0) {
            currentLine.lastSpaceIndex = cursor - 1
            currentLine.charAlignment[cursor - 1] = 1
        }
        cursor += wordLength + 1
    }
//    println("Alignment: $lineWidth")
//    alignment.forEach {
//        println(it.toString())
//    }
    return alignment
}

fun findLongestRiver(lines: List<Line>) : Int {
    var maxLength = 0
    for (i in lines.indices) {
        for (j in 0..lines[i].lastSpaceIndex) {
            if (lines[i].charAlignment[j] == 1) {
                if (i > 0) {
                    lines[i].charAlignment[j] += findUpRiverMaxLength(lines[i - 1], j)
                }
                maxLength = maxOf(maxLength, lines[i].charAlignment[j])
            }
        }
    }
    return maxLength
}

fun findUpRiverMaxLength(line: Line, location: Int): Int {
    if (location > line.lastSpaceIndex + 1) {
        return 0
    }
    var maxLength = line.charAlignment[location]
    if (location < line.lineWidth - 1) {
        maxLength = maxOf(maxLength, line.charAlignment[location + 1])
    }
    if (location > 0) {
        maxLength = maxOf(maxLength, line.charAlignment[location - 1])
    }
    return maxLength
}

fun readWords(wordCount: Int, lineProvider: () -> String): MutableList<Byte> {
    var i = 0
    val words = mutableListOf<Byte>()
    while (i < wordCount) {
        val lineWords = lineProvider.invoke().split(" ")
        lineWords.forEach {
            words.add(it.length.toByte())
            i++
        }
    }
    return words
}

class Line(val lineWidth: Int) {
    val charAlignment = IntArray(lineWidth)
    var lastSpaceIndex = -1

    override fun toString(): String {
        return charAlignment.contentToString()
    }
}