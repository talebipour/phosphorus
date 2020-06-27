package com.github.talebipour

fun main() {
    val input = readLine()!!.split(" ")
    val a = input[0].toLong()
    val b = input[1].toLong()
    println(findK(maxOf(a, b), minOf(a, b)))
}

fun findK(a: Long, b: Long) : Long {
    var minLcm = lcm(a, b)
    var k = 0L
    for (i in 1..a) {
        var lcm = lcm(a + i, b + i)
        if (lcm < minLcm) {
            k = i
            minLcm = lcm
        }
        if (a + i >= lcm) {
            break
        }
    }
    return k
}

fun lcm(a: Long, b: Long) : Long {
    var c = a
    var d = b
    while (c % d != 0L) {
        val temp = d
        d = c % d
        c = temp
    }
    return (a / d) * b
}