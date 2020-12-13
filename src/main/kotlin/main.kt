fun main(args: Array<String>) {
    println("Hello World!")
    val input = FileProvider.readStrings("src/main/resources/input12.txt")

    for (line in input) {
        println(line)
    }
}