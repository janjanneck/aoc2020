fun main() {
    val input = FileProvider.readStrings("src/main/resources/input17.txt")

    val conwayCubes = mutableMapOf<Coordinate, Char>()

    for (i in 0 until input.size) {
        val line = input[i]
        for (j in line.indices) {
            conwayCubes[Coordinate(j, i, 0)] = line[j]
        }
    }

    printInfo(conwayCubes)

    for (cycle in 0..5) {

        updateCoordinatesToCheck(conwayCubes)

        val changeList = mutableMapOf<Coordinate, Char>()

        for ((coor, value) in conwayCubes) {
            val neighbours = getNeighbours(coor)
            var activeNeighbourCount = 0
            for (neighbour in neighbours) {
                if (conwayCubes.getOrElse(neighbour, { '.' }) == '#') {
                    activeNeighbourCount++
                }
            }

            if (value == '.') { // Cube is inactive
                if (activeNeighbourCount == 3) {
                    changeList[coor] = '#' // Cube becomes active
                }
            } else if (value == '#') { // Cube is active
                if (!(activeNeighbourCount == 2 || activeNeighbourCount == 3)) {
                    changeList[coor] = '.' // Cube becomes inactive
                }
            } else assert(false)

        }
        for ((coor, value) in changeList) {
            conwayCubes[coor] = value
        }

        printInfo(conwayCubes)
    }

    println("After 6. cycle, ${conwayCubes.values.filter { it == '#' }.count()} are active.")

}

private fun printInfo(conwayCubes: MutableMap<Coordinate, Char>) {
    for (z in conwayCubes.keys.map { it.z }.min()!!..conwayCubes.keys.map { it.z }.max()!!) {
        println("Z = $z")
        for (y in conwayCubes.keys.map { it.y }.min()!!..conwayCubes.keys.map { it.y }.max()!!) {
            for (x in conwayCubes.keys.map { it.x }.min()!!..conwayCubes.keys.map { it.x }.max()!!) {
                print(conwayCubes[Coordinate(x, y, z)])
            }
            println()
        }
        println()
    }
    println("========")
}

fun updateCoordinatesToCheck(currentLayout: MutableMap<Coordinate, Char>) {
    val xMax = currentLayout.keys.map { it.x }.maxOrNull()!!
    val xMin = currentLayout.keys.map { it.x }.minOrNull()!!
    val yMax = currentLayout.keys.map { it.y }.maxOrNull()!!
    val yMin = currentLayout.keys.map { it.y }.minOrNull()!!
    val zMax = currentLayout.keys.map { it.z }.maxOrNull()!!
    val zMin = currentLayout.keys.map { it.z }.minOrNull()!!

    for (i in xMin - 1 .. xMax + 1) {
        for (j in yMin - 1 .. yMax + 1) {
            for (k in zMin - 1 .. zMax + 1) {
                currentLayout.computeIfAbsent(Coordinate(i, j, k)) { '.' }
            }
        }
    }
}

fun getNeighbours(pos: Coordinate): MutableList<Coordinate> {
    val neighbours = mutableListOf<Coordinate>()
    for (i in pos.x - 1 .. pos.x + 1) {
        for (j in pos.y - 1 .. pos.y + 1) {
            for (k in pos.z - 1 .. pos.z + 1) {
                neighbours.add(Coordinate(i, j, k))
            }
        }
    }
    neighbours.remove(pos)
    return neighbours
}

data class Coordinate(val x:Int, val y:Int, val z:Int)