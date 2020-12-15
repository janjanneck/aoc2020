fun main() {
    val input = FileProvider.readStrings("src/main/resources/input15.txt")

    val starters = input[0].split(',').map { it.toInt() }

    val lastSpokenTurns = mutableMapOf<Int, Int>()

    starters.forEachIndexed { turn, number ->
        lastSpokenTurns[number] = turn + 1
    }
    lastSpokenTurns.remove(starters.last())

    var lastSpokenNum = starters.last()

    for (turn in (starters.size + 1)..30000000) {
        val previousTurnOfLastSpoken = lastSpokenTurns[lastSpokenNum]
        lastSpokenTurns[lastSpokenNum] = turn - 1 // last turn
        lastSpokenNum = if (previousTurnOfLastSpoken != null) turn - 1 - previousTurnOfLastSpoken else 0
    }

    println("30000000th number spoken: $lastSpokenNum")

}