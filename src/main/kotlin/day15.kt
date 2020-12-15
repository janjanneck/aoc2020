fun main() {
    val input = FileProvider.readStrings("src/main/resources/input15.txt")

    val starters = input[0].split(',').map { it.toInt() }

    var i = starters.size + 1

    val spokenNumbers = mutableListOf<Int>()
    spokenNumbers.addAll(starters)

    var spokenNumbersSize = spokenNumbers.size

    var lastSpoken = spokenNumbers.last()

    while (i <= 30000000) {
        if (i % 10000 == 0) println("I is at $i")
        val spokenBefore = spokenNumbers.indexOf(lastSpoken) != spokenNumbersSize - 1
        val nextNum = if (spokenBefore) {
            (spokenNumbersSize - 1) - spokenNumbers.subList(0, spokenNumbersSize - 1)
                .lastIndexOf(lastSpoken)
        } else 0
        lastSpoken = nextNum
        spokenNumbers.add(nextNum)
        spokenNumbersSize++
        i++;
    }

    println("30000000th number spoken: ${spokenNumbers.last()}")

}