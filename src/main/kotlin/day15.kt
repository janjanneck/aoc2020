fun main() {
    val input = FileProvider.readStrings("src/main/resources/input15.txt")

    val starters = input[0].split(',').map { it.toInt() }

    val lastSpokenTurns = mutableMapOf<Int, Int>()

    starters.forEachIndexed { turn, number ->
        lastSpokenTurns[number] = turn + 1
    }
    lastSpokenTurns.remove(starters.last())

    var currentTurn = starters.size + 1
    var lastSpokenNum = starters.last()
    while (currentTurn <= 30000000) {
        val previousTurnOfLastSpoken = lastSpokenTurns[lastSpokenNum]
        val saidBefore = previousTurnOfLastSpoken != null

        // Update lastSpokenTurn of lastSpokenNum to to previous turn
        lastSpokenTurns[lastSpokenNum] = currentTurn - 1
        lastSpokenNum = if (saidBefore) currentTurn - 1 - previousTurnOfLastSpoken!! else 0
        currentTurn++
    }

    println("30000000th number spoken: $lastSpokenNum")

}