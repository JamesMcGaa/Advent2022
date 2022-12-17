import java.io.File
import java.lang.Integer.max
import kotlin.Exception
import kotlin.math.absoluteValue

fun main() {
    var mostSoFar = 0
    val valves = mutableListOf<Valve>()
    val lines = File("inputs/input16.txt").forEachLine {
        valves.add(Valve(it))
    }
    val nonzeroValves = valves.filter { valve -> valve.flow > 0 }
    val valveMap = hashMapOf<String, Valve>()
    valves.forEach { valve ->
        valveMap[valve.ID] = valve
    }

    val memo = hashMapOf(Triple(0,0,"AA") to 0)
    var frontier = hashSetOf(Triple(0,0,"AA"))

    for (time in 1 .. 30) {
        println(time)
        var newFrontier = hashSetOf<Triple<Int,Int,String>>()
        while (frontier.isNotEmpty()) {
            val current = frontier.pop()!!
            val currentState = current.first
            val currentValve = valveMap[current.third]!!
            val inc = getInc(currentState, nonzeroValves)

            // If its nonzero and not already activated
            if (nonzeroValves.contains(currentValve)) {
                val valveIdx = nonzeroValves.indexOf(currentValve)
                if (!isOn(currentState, valveIdx)) {
                    val switchedOn = Triple(switchOn(currentState, valveIdx), time, currentValve.ID)
                    newFrontier.add(switchedOn)
                    memo[switchedOn] = max(memo[current]!! + inc, memo.getOrDefault(switchedOn, 0))
                }
            }

            currentValve.adj.forEach {
                adjID ->
                val visited = Triple(currentState, time, adjID)
                newFrontier.add(visited)
                memo[visited] = max(memo[current]!! + inc, memo.getOrDefault(visited, 0))
            }
        }

        frontier = newFrontier.toHashSet() //copy
    }

    println(memo.values.max())

}

fun isOn(state: Int, index: Int): Boolean {
    return state and (1 shl index) > 0
}
fun switchOn(state: Int, index: Int): Int {
    return state or (1 shl index)
}

fun getInc(state: Int, nonzeroValves: List<Valve>): Int {
    var totalFlow = 0
    for (i in 0 .. 14) {
        if ((state and (1 shl i)) > 0) {
            totalFlow += nonzeroValves[i].flow
        }
    }
    return totalFlow
}

fun <T> MutableSet<T>.pop(): T? = this.first().also{this.remove(it)}

class Valve (
    input: String
) {
    val flow: Int
    val ID: String
    val adj: List<String>

    init {
        ID = input.substring(6,8)
        flow = Integer.parseInt(input.substring(23, input.indexOf(';')))
        adj = input.substring(input.indexOf("tunnel") + 22).split(',').map { it.trim() }
    }
}

 
