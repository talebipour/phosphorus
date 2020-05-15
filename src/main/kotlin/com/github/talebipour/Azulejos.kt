package com.github.talebipour

import java.io.BufferedWriter

fun main() {
    val tileNo = readLine()!!.toInt()

    val backTiles = readTileRow(tileNo)
    val frontTiles = readTileRow(tileNo)

    backTiles.sortWith(compareBy(Tile::price).thenComparing(Tile::height))
    frontTiles.sortWith(compareBy(Tile::price).thenComparing(Tile::height))

    println(backTiles.contentToString())
    println(frontTiles.contentToString())
    reorderSamePrices(backTiles, frontTiles)
    println(backTiles.contentToString())
    println(frontTiles.contentToString())

    for (i in 0 until tileNo) {
        if (backTiles[i].height <= frontTiles[i].height) {
            println("impossible")
            return
        }
    }

    val writer = System.out.bufferedWriter()
    printRow(writer, backTiles)
    printRow(writer, frontTiles)
    writer.flush()
}

private fun reorderSamePrices(backTiles: Array<Tile>, frontTiles: Array<Tile>) {
    val tileNo = backTiles.size
    for (i in 0 until tileNo) {
        if (backTiles[i].height <= frontTiles[i].height) {
            for (j in i + 1 until tileNo) {
                if (backTiles[i].price != backTiles[j].price) {
                    break
                }
                if (backTiles[i].height > frontTiles[j].height && backTiles[j].height > frontTiles[i].height) {
                    swapItems(backTiles, i , j)
                }
            }
            for (j in i - 1 downTo 0) {
                if (backTiles[i].price != backTiles[j].price) {
                    break
                }
                if (backTiles[i].height > frontTiles[j].height && backTiles[j].height > frontTiles[i].height) {
                    swapItems(backTiles, i , j)
                }
            }
        }
    }
    for (i in 0 until tileNo) {
        if (backTiles[i].height <= frontTiles[i].height) {
            for (j in i + 1 until tileNo) {
                if (frontTiles[i].price != frontTiles[j].price) {
                    break
                }
                if (backTiles[i].height > frontTiles[j].height && backTiles[j].height > frontTiles[i].height) {
                    swapItems(frontTiles, i , j)
                }
            }
            for (j in i - 1 downTo 0) {
                if (frontTiles[i].price != frontTiles[j].price) {
                    break
                }
                if (backTiles[i].height > frontTiles[j].height && backTiles[j].height > frontTiles[i].height) {
                    swapItems(frontTiles, i , j)
                }
            }
        }
    }
}

fun swapItems(row: Array<Tile>, index1: Int, index2: Int){
    var temp = row[index1]
    row[index1] = row[index2]
    row[index2] = temp
}

private fun printRow(writer: BufferedWriter, backTiles: Array<Tile>) {
    for (i in 0 until backTiles.size - 1) {
        writer.append(backTiles[i].index.toString()).append(' ')
    }
    writer.appendln(backTiles.last().index.toString())
}

fun readTileRow(tileNo: Int) : Array<Tile> {
    val prices = readIntArray()
    val heights = readIntArray()

    return Array(tileNo) {
        Tile(it + 1, prices[it], heights[it])
    }
}

fun readIntArray() : IntArray {
    return readLine()!!.split(" ").stream().mapToInt(String::toInt).toArray()
}

class Tile(val index: Int, val price: Int, val height: Int) {
    override fun toString(): String {
        return "(index=$index, price=$price, height=$height)"
    }
}
