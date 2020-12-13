import kotlin.math.floor

fun main() {
    val input = FileProvider.readStrings("src/main/resources/input13.txt")

    val myArrival = input[0].toInt()
    val buslines = input[1].split(',').filter { it != "x" }.map { it.toInt() }.sorted()

    buslines.forEach { lineNr ->
        val lastDeparture = floor((myArrival / lineNr).toDouble()) * lineNr
        val nextDeparture = lastDeparture + lineNr
        val waitingTime = nextDeparture - myArrival
        println("Bus line $lineNr arrives ${waitingTime.toInt()} minutes after arrival")
    }
}
