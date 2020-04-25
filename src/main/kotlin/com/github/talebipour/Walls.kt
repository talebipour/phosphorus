package com.github.talebipour

import java.io.InputStream
import java.lang.AssertionError
import java.util.*
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

class Walls {

    companion object {
        var radius = 0
        val cranes = mutableListOf<Crane>()
        val walls = mutableListOf<Wall>()
        fun solve(input: InputStream): String {
            readInputs(input)
            var maxDegree = 0
            for (crane in cranes) {
                for (wall in walls) {
                    if (crane.reaches(wall)) {
                        crane.reachableWalls.add(wall)
                        wall.reachableCranes.add(crane)
                    }
                }
                maxDegree = max(maxDegree, crane.degree())
            }
            if (walls.any { it.reachableCranes.isEmpty() }) {
                return "Impossible"
            }
            return when (maxDegree) {
                4 -> "1"
                3 -> "2"
                2 -> solveDegree2()
                1 -> "4"
                else -> throw AssertionError("Unexpected degree: $maxDegree")
            }
        }

        private fun solveDegree2(): String {
            val targets = cranes.filter { it.reachableWalls.size == 2 }
            for (crane in targets) {
                for (other in targets) {
                    if (crane != other && crane.reachableWalls.intersect(other.reachableWalls).isEmpty()) {
                        return "2"
                    }
                }
            }
            return "3"
        }

        private fun readInputs(input: InputStream) {
            val br = input.bufferedReader()

            val firstLine = StringTokenizer(br.readLine(), " ")
            val l = firstLine.nextToken().toInt()
            val w = firstLine.nextToken().toInt()
            walls.add(Wall(-l / 2.0, 0.0))
            walls.add(Wall(0.0, -w / 2.0))
            walls.add(Wall(l / 2.0, 0.0))
            walls.add(Wall(0.0, w / 2.0))
            val n = firstLine.nextToken().toInt()
            radius = firstLine.nextToken().toInt()
            for (i in 1..n) {
                val craneStr = br.readLine().split(" ")
                val crane = Crane(craneStr[0].toInt(), craneStr[1].toInt())
                cranes.add(crane)
            }
        }
    }

    class Crane(private val x: Int, private val y: Int) {
        val reachableWalls = mutableListOf<Wall>()
        fun reaches(wallCenter: Wall): Boolean {
            return sqrt((wallCenter.centerX - x).pow(2) + (wallCenter.centerY - y).pow(2)) <= radius
        }

        fun degree(): Int {
            return reachableWalls.size
        }
    }

    class Wall(val centerX: Double, val centerY: Double) {
        val reachableCranes = mutableListOf<Crane>()
    }
}

fun main() {
    println(Walls.solve(System.`in`))
}