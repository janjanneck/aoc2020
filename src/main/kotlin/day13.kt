import kotlin.math.floor
import kotlin.system.exitProcess

fun main() {
    val input = FileProvider.readStrings("src/main/resources/input13.txt")

    val buslines = input[1].split(',')

    val hash:MutableMap<Int, Int> = HashMap()

    buslines.forEachIndexed { index, s ->
        if (s!="x") {
            hash[s.toInt()] = index;
        }
    }

    val sortedEntries = hash.entries.sortedByDescending { it.key }

    var i = 1L

    loop@ while (i < Long.MAX_VALUE){

        for ((lineNr, offset) in sortedEntries){
            if ((i + offset) % lineNr != 0L) {
                i++
                continue@loop
            }
        }

        println("I is at $i")
        exitProcess(100)
    }


}

private fun printInfo(input: MutableList<String>) {
    val buslines = input[1].split(',').filter { it != "x" }.map { it.toInt() }

    print("  t   ")

    buslines.forEach { print("  $it ") }
    println()

    for (i in 0..100000000000000) {
        print("  $i  ")
        for (line in buslines) {
            if (i % line == 0L) print("  D  ") else print("  .  ")
        }
        println()
    }
}
