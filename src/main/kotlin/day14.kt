import kotlin.math.pow

fun main() {
    val input = FileProvider.readStrings("src/main/resources/input14.txt")

    val mem = HashMap<Long, Long>()

    var currentMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

    for (line in input) {
        when {
            line.startsWith("mask") -> currentMask = line.substring(line.indexOf('=') + 2)
            line.startsWith("mem") -> {
                val address = line.substring(line.indexOf('[') + 1, line.indexOf(']')).toInt()
                val value = line.substring(line.indexOf('=') + 2).toInt()
                val maskedAddress = applyMask(currentMask, address)
                val adresses = resolveAddress(maskedAddress)

                for (addr in adresses) {
                    mem[addr] = value.toLong()
                }
            }
            else -> throw Exception()
        }
    }

    val sum = mem.values.sum()
    println("Sum in memory is $sum")

}

fun resolveAddress(maskedAdd: String): List<Long> {
    val xpos = mutableListOf<Int>()

    val returnAddresses = mutableListOf<String>()

    maskedAdd.forEachIndexed { index, c -> if (c == 'X') xpos.add(index) }

    val distinctAddresses = 2.0.pow(xpos.size.toDouble())

    val binAddresses = mutableListOf<String>()

    for (i in 0 until distinctAddresses.toInt()) {
        binAddresses.add(i.toString(2))
    }

    val binAddrMaxLength = binAddresses.map { it.length }.maxOrNull()!!
    val bins = binAddresses.map { it.padStart(binAddrMaxLength, '0' )}


    for (element in bins) {
        val temp = maskedAdd.toCharArray()
        for (j in 0 until xpos.size) {
            temp[xpos[j]] = element[j]
        }
        returnAddresses.add(temp.concatToString())
    }

    return returnAddresses.map { it.toLong(2) }
}

fun applyMask(currentMask: String, address: Int): String {
    val bin = Integer.toBinaryString(address).reversed()
    val returnValue = StringBuilder()
    currentMask.reversed().forEachIndexed { index, bitMaskBit ->
        if (index >= bin.length) {
            returnValue.append(bitMaskBit)
        } else {
            when (bitMaskBit) {
                '0' -> returnValue.append(bin[index])
                '1' -> returnValue.append("1")
                'X' -> returnValue.append("X")
                else -> throw Exception()
            }
        }
    }
    return returnValue.toString().reversed()
}
