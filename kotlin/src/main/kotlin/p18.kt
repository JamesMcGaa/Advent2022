import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    var counter = 0
    val cubes = hashSetOf<Triple<Int, Int, Int>>()
    File("inputs/input18.txt").forEachLine {
        line ->
        val ints = line.split(',').map { Integer.parseInt(it.trim()) }
        val triple = Triple(ints[0], ints[1], ints[2])
        val others = mutableListOf(
            Triple(ints[0]-1, ints[1], ints[2]),
            Triple(ints[0]+1, ints[1], ints[2]),
            Triple(ints[0], ints[1]-1, ints[2]),
            Triple(ints[0], ints[1]+1, ints[2]),
            Triple(ints[0], ints[1], ints[2]-1),
            Triple(ints[0], ints[1], ints[2]+1),
        )
        cubes.add(triple)
        counter += 6
        for (other in others) {
            if (cubes.contains(other)) {
                counter -= 2
            }
        }
    }
    println(counter)
    println(calculateTotalSA(cubes))
    val xMin = cubes.minOf { cube -> cube.first }
    val xMax = cubes.maxOf { cube -> cube.first }
    val yMin = cubes.minOf { cube -> cube.second }
    val yMax = cubes.maxOf { cube -> cube.second }
    val zMin = cubes.minOf { cube -> cube.third }
    val zMax = cubes.maxOf { cube -> cube.third }
    println("${xMin}, ${xMax}, ${yMin}, ${yMax}, ${zMin}, ${zMax}, ")

    // Idea 1: BFS to find the connected components that cannot find an 'exit' then take the total surface area of these pockets

    // Idea 2: Flood fill a corner of a cube that fully contains our cube + 1 in each dimension
}

fun calculateTotalSA(cubes: HashSet<Triple<Int, Int, Int>>): Int {
    val reconstructed = hashSetOf<Triple<Int, Int, Int>>()
    var counter = 0
    for (other in cubes) {
        reconstructed.add(other)
        counter += 6
        if (reconstructed.contains(other)) {
            counter -= 2
        }
    }
    return counter
}
 
