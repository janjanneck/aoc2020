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

    loop@ for (i in 100000000000000..Long.MAX_VALUE){
        if (i%100000000 == 0L) println("Checking $i")
        if (i % buslines[0].toLong() != 0L) continue

        for (offset in buslines.indices) {
            val lineNr = buslines[offset]
            if (lineNr != "x"){
                if ((i + offset) % lineNr.toInt() != 0L) {
                    continue@loop
                }
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
