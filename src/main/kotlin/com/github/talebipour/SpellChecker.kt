package com.github.talebipour

import java.lang.StringBuilder

fun main() {
    val wrongWord = readLine()
    val correctWord = readLine()

    if (wrongWord!!.length != correctWord!!.length + 1) {
        println(0)
        return
    }

    var wrongIndex = 0
    var sameInSequence = 1
    for (i in wrongWord.length - 1 downTo 0) {
        if (i < wrongWord.length - 1 && wrongWord[i] == wrongWord[i + 1]) {
            sameInSequence++
        } else {
            sameInSequence = 1
        }
        if (i > 0 && wrongWord[i] != correctWord[i - 1]) {
            wrongIndex = i
            break
        }
    }

    for (i in 0 until wrongIndex) {
        if (wrongWord[i] != correctWord[i]) {
            println(0)
            return
        }
    }

    println(sameInSequence)
    val output = StringBuilder()
    for (i in 1 until sameInSequence) {
        output.append(wrongIndex + i).append(' ')
    }
    output.append(wrongIndex + sameInSequence)
    println(output)
}
