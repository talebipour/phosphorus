package com.github.talebipour

import com.github.talebipour.CountTriangles.solve
import org.junit.Assert.*
import org.junit.Test

class CountTrianglesTest {

    @Test
    fun testSample1 () {
        assertEquals(4L, solve(1, 2, 3, 4))
    }

    @Test
    fun testSample2 () {
        assertEquals(3L, solve(1, 2, 2, 5))
    }

    @Test
    fun testSample3 () {
        assertEquals(1L, solve(50_0000, 50_0000, 50_0000, 50_0000))
    }

    @Test
    fun testSample4 () {
        assertEquals(56661L, solve(100, 200, 250, 260))
    }

    @Test
    fun testSampleHuge1 () {
        val start = System.currentTimeMillis()
        assertEquals(300031250125000L, solve(1, 25_0000, 49_0000, 50_0000))
        println("Finished in ${System.currentTimeMillis() - start}ms")
    }
    @Test
    fun testSampleHuge2 () {
        val start = System.currentTimeMillis()
        assertEquals(47009400470000L, solve(1, 480_000, 490_000, 500_000))
        println("Finished in ${System.currentTimeMillis() - start}ms")
    }
}