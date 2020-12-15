fun main() {
    val input = FileProvider.readStrings("src/main/resources/input15.txt")

    val starters = input[0].split(',').map { it.toInt() }

    var i = starters.size + 1

    val spokenNumbers = mutableListOf<Int>()
    spokenNumbers.addAll(starters)

    while (i <= 2020) {
        val spokenBefore = spokenNumbers.indexOf(spokenNumbers.last()) != spokenNumbers.size - 1
        val temp = spokenNumbers.subList(0, spokenNumbers.size - 1)
        val nextNum = if (spokenBefore) spokenNumbers.size - 1 - temp.lastIndexOf(spokenNumbers.last()) else 0
        spokenNumbers.add(nextNum)
        i++;
    }

    println("2020th number spoken: ${spokenNumbers.last()}")

}