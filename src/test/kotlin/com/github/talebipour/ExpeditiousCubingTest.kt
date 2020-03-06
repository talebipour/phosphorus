package com.github.talebipour

import ExpeditiousCubing
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ExpeditiousCubingTest {

    @Test
    fun test1() {
        test("""6.38 7.20 6.95 8.11
7.53""", "infinite")
    }

    @Test
    fun test2() {
        test("""6.38 7.20 6.95 8.11
6.99""", "6.82")
    }

    @Test
    fun test3() {
        test("""6.38 7.20 6.95 8.11
6.45""", "impossible")
    }

    fun test(input : String, expectedOutput : String) {
        val output = ByteArrayOutputStream()
        ExpeditiousCubing.solve(input.byteInputStream(), PrintStream(output))
        assertEquals(expectedOutput, output.toString())
    }
}