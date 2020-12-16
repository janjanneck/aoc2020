fun main() {
    val input = FileProvider.readStrings("src/main/resources/input16.txt")

    val requirements = input.subList(0, input.indexOf(""))
    val myTicket = input.subList(input.indexOf("your ticket:") + 1, input.lastIndexOf(""))
    val otherTickets = input.subList(input.indexOf("nearby tickets:") + 1, input.lastIndex +1)

    val removals = listOf<String>("departure location: ", "departure station: ", "departure platform: ",
    "departure track: ", "departure date: ", "departure time: ")

    val regex = Regex("(\\d+-\\d+)\\sor\\s(\\d+-\\d+)")

    val validRanges = mutableListOf<String>()

    for (req in requirements) {
        val numbers = regex.find(req)?.groupValues
        if (numbers != null) {
            validRanges.add(numbers[1])
            validRanges.add(numbers[2])
        }
    }

    val validNumbers = mutableSetOf<Int>()

    for (range in validRanges) {
        val first = range.substring(0, range.indexOf('-')).toInt()
        val second = range.substring(range.indexOf('-') + 1, range.length).toInt()

        for (n in first..second) {
            validNumbers.add(n)
        }
    }

    val numbersToCheck = mutableListOf<Int>()

    for (ticket in otherTickets) {
        numbersToCheck.addAll(ticket.split(',').map { it.toInt() })
    }

    val invalids = mutableListOf<Int>()
    for (check in numbersToCheck) {
        if (!validNumbers.contains(check)) {
            invalids.add(check)
        }
    }

    println("Invalid numbers: $invalids")
    println("Ticket scanning error rate: ${invalids.sum()}")
}