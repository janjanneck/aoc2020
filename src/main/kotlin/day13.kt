import java.util.*
import kotlin.collections.HashSet
import kotlin.system.exitProcess

fun main() {
    val input = FileProvider.readStrings("src/main/resources/input13.txt")

    val buslines = input[1].split(',')

    val hash = TreeMap<Int, Int>()

    buslines.forEachIndexed { index, s ->
        if (s != "x") {
            hash[s.toInt()] = index;
        }
    }

    val sortedEntries = hash.entries

    val foundSequences = HashSet<Int>()

    var jumpAheadNr = 1L
    var i = 1L

    whi@ while (i < Long.MAX_VALUE) {
        loop@ for ((lineNr, offset) in sortedEntries) {
            if ((i + offset) % lineNr == 0L) {
                foundSequences.add(lineNr)
                jumpAheadNr = lcm(foundSequences.toList().map { it.toLong() })
                println("$foundSequences -> $jumpAheadNr")
                // check following busses
                continue@loop
            } else {
                // increment by jumpAheadNr
                i += jumpAheadNr
                continue@whi
            }

        }
        println("I is at $i")
        exitProcess(100)
    }
}

fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

fun lcm(inp: List<Long>): Long {
    var result = inp[0]
    for (i in 1 until inp.size) {
        result = lcm(result, inp[i])
    }
    return result
}

fun gcd(a: Long, b: Long): Long {
    var a_val = a
    var b_val = b
    while (b_val > 0) {
        val temp = b_val
        b_val = a_val % b_val
        a_val = temp
    }
    return a_val
}

fun gcd(inp: List<Long>): Long {
    var result = inp[0]

    for (i in 1 until inp.size) {
        result = gcd(result, inp[i])
    }
    return result

}

private fun printInfo(input: MutableList<String>) {
    val buslines = input[1].split(',').filter { it != "x" }.map { it.toInt() }

    print("  t   ")

    buslines.forEach { print("  $it ") }
    println()

    for (i in 0..100000L) {
        print("  $i  ")
        for (line in buslines) {
            if (i % line == 0L) print("  D  ") else print("  .  ")
        }
        println()
    }
}
