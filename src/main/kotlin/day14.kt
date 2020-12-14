fun main() {
    val input = FileProvider.readStrings("src/main/resources/input14.txt")

    val mem = HashMap<Int, Long>()

    var currentMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

    for (line in input) {
        when {
            line.startsWith("mask") -> currentMask = line.substring(line.indexOf('=') + 2)
            line.startsWith("mem") -> {
                val address = line.substring(line.indexOf('[') +1, line.indexOf(']')).toInt()
                val value = line.substring(line.indexOf('=') + 2).toInt()
                val appliedValue = applyMask(currentMask, value)
                mem[address] = appliedValue
            }
            else -> throw Exception()
        }
    }

    val sum = mem.values.sum()
    println("Sum in memory is $sum")

}

fun applyMask(currentMask: String, value: Int): Long {
    val bin = Integer.toBinaryString(value).reversed()
    val returnValue = StringBuilder()
    currentMask.reversed().forEachIndexed { index, c ->
        if (index >= bin.length) {
            returnValue.append(if (c == 'X') "0" else c)
        } else {
            when (c) {
                '1' -> returnValue.append("1")
                '0' -> returnValue.append("0")
                'X' -> returnValue.append(bin[index])
                else -> throw Exception()
            }
        }
    }
    return returnValue.toString().reversed().toLong(2)
}
