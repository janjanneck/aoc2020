fun main() {
    val input = FileProvider.readStrings("src/main/resources/input16.txt")

    val requirements = input.subList(0, input.indexOf(""))
    val myTicket = input.subList(input.indexOf("your ticket:") + 1, input.lastIndexOf(""))
    val otherTickets = input.subList(input.indexOf("nearby tickets:") + 1, input.lastIndex +1)

    val regex = Regex("(\\d+-\\d+)\\sor\\s(\\d+-\\d+)")

    val validRanges = mutableListOf<String>()

    val reqToRange = mutableMapOf<Int, List<Int>>()

    for (req in requirements) {
        val numbers = regex.find(req)?.groupValues
        if (numbers != null) {
            validRanges.add(numbers[1])
            validRanges.add(numbers[2])
        }
    }

    for (i in 0 until requirements.size) {
        val req = requirements[i]
        val numbers = regex.find(req)?.groupValues
        if (numbers != null) {
            reqToRange[i] = getNumbersFromRange(numbers[1]) + getNumbersFromRange(numbers[2])
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

    // Check if a ticket is invalid and then discard it


    println("Invalid numbers: $invalids")
    println("Ticket scanning error rate: ${invalids.sum()}")

    println(otherTickets)
    println(myTicket)

    val otherTicketNums = otherTickets
        .map { ticket -> ticket.split(',').map { it.toInt() } }
        .mapNotNull { it.takeIf { it.intersect(invalids).isEmpty() } }

    val ticketFieldCount = otherTicketNums?.get(0)?.size
    val possibleValidColumns = mutableMapOf<Int, MutableList<Int>>()

    // Check for every requirement
    for (requirementIndex in 0 until reqToRange.keys.size) {
        // Check every column
            for (columnIndex in 0 until ticketFieldCount!!) {
                // Check every ticket
                    val numsInColumn = mutableListOf<Int>()
                for (ticket in 0 until otherTicketNums.size) {
                    // Add value of ticket in column to numsInColumn
                    otherTicketNums[ticket]?.get(columnIndex)?.let { numsInColumn.add(it) }
                }
                // Check if all elements in the column are valid
                if(reqToRange[requirementIndex]!!.containsAll(numsInColumn)) {
                    if(possibleValidColumns.containsKey(requirementIndex)) {
                        possibleValidColumns[requirementIndex]?.add(columnIndex)
                    } else {
                        possibleValidColumns[requirementIndex] = mutableListOf(columnIndex)
                    }
                }
            }
    }

    println(possibleValidColumns)

    var termCond = true
    while (termCond) {
        val singleValues = possibleValidColumns.filter { it.value.size == 1}
        val keys = singleValues.keys
        val valuesToRemove = singleValues.values.flatMap { it.toList() }
        // Remove singleValues from all other rows
        for ((k,v) in possibleValidColumns) {
            if (!keys.contains(k)) {
                if(possibleValidColumns[k]?.removeAll(valuesToRemove) == true) {
                    println("Removing ${singleValues.values.toList()[0]}")
                }
            }
        }
        termCond = possibleValidColumns.values.any { it.size > 1 }
    }

    println(possibleValidColumns)

    var sum:Long = 1
    for (i in 0..5) {
        sum *= myTicket[0].split(',')[possibleValidColumns[i]!!.sum()].toLong()
    }
    println(sum)

}

fun getNumbersFromRange(range: String): List<Int> {
    val validNumbers = mutableListOf<Int>()
    val first = range.substring(0, range.indexOf('-')).toInt()
    val second = range.substring(range.indexOf('-') + 1, range.length).toInt()

    for (n in first..second) {
        validNumbers.add(n)
    }
    return validNumbers.toList()
}