import java.io.File
import java.lang.Exception
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main(args: Array<String>) {

    val tailVisited = HashSet<Pair<Int, Int>>()
    var tailLoc = Pair(0,0)
    tailVisited.add(tailLoc)

    var headLoc = Pair(0,0)
    File("inputs/input9.txt").forEachLine { line ->
        val moves = line.split(" ")
        val amount = Integer.parseInt(moves[1])
        for (i in 1..amount) {
            headLoc = when (moves[0]) {
                "R" -> Pair(headLoc.first + 1, headLoc.second)
                "L" -> Pair(headLoc.first - 1, headLoc.second)
                "U" -> Pair(headLoc.first, headLoc.second + 1)
                "D" -> Pair(headLoc.first, headLoc.second - 1)
                else -> throw Exception()
            }
            tailLoc = newTailLoc(tailLoc, headLoc)
            tailVisited.add(tailLoc)
        }
    }
    println(tailVisited.size)


}

fun newTailLoc(tailLoc: Pair<Int, Int>, headLoc: Pair<Int, Int>): Pair<Int, Int> {
    val deltaX = headLoc.first - tailLoc.first
    val deltaY = headLoc.second - tailLoc.second
    if (deltaX.absoluteValue > 1 || deltaY.absoluteValue > 1) {
        return Pair(tailLoc.first + deltaX.sign, tailLoc.second + deltaY.sign)
    }
    return tailLoc
}
