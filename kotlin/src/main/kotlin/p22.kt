import java.io.File

fun main() {
    var counter = 0L
    val lines = File("src/input22.txt").readLines()

    val height = lines.size - 2
    val width = lines.subList(0, height).maxBy { it.length }!!.length
    val firstNonzeroSecondCoord = lines[0].indexOfFirst { ch -> ch != ' ' }
    val startLoc = Pair(0, firstNonzeroSecondCoord)
    println("${height}, ${width}, $firstNonzeroSecondCoord")

    val board = HashMap<Pair<Int, Int>, Char>()
    for (i in lines.indices) {
        for (j in lines[i].indices) {
            board[Pair(i, j)] = lines[i][j]
        }
    }

    val operations = lines[lines.size - 1]
    val parsedOps = mutableListOf<String>()
    var curr = ""
    for (ch in operations) {
        when (ch) {
            'L', 'R' -> {
                if (curr.isNotEmpty()) {
                    parsedOps.add(curr)
                    curr = ""
                }
                parsedOps.add(ch.toString())
            }
            else -> curr += ch
        }
    }
    if (curr.isNotEmpty()) {
        parsedOps.add(curr)
    }
    println(parsedOps)
    val character = Character(board, width, height, startLoc)

    for (op in parsedOps) {
        character.handle(op)
    }
    println(1000 * (character.loc.first + 1) + 4 * (character.loc.second + 1) + character.facing)
}

class Character(val board: HashMap<Pair<Int, Int>, Char>, val width: Int, val height: Int, startLoc: Pair<Int, Int>) {
    var loc = startLoc
    var facing = 0

    fun handle(command: String) {
        print("$loc, $facing -> ")
        when (command) {
            "L" -> facing = Math.floorMod(facing - 1, 4)
            "R" -> facing = Math.floorMod(facing + 1, 4)
            else -> moveForward(Integer.parseInt(command))
        }
        print("$loc, $facing \n")
    }

    private fun moveForward(amount: Int) {
        if (amount == 0) {
            return
        }
        when (facing) {
            0 -> {
                var adj = Math.floorMod(loc.second + 1, width)
                if (board[Pair(loc.first, adj)] == '#') {
                    return
                }
                while (board[Pair(loc.first, adj)] == null || board[Pair(loc.first, adj)] == ' ') {
                    adj = Math.floorMod(adj + 1, width)
                }
                if (board[Pair(loc.first, adj)] == '#') {
                    return
                }
                loc = Pair(loc.first, adj)
                moveForward(amount - 1)
            }

            1 -> {
                var adj = Math.floorMod(loc.first + 1, height)
                if (board[Pair(adj, loc.second)] == '#') {
                    return
                }
                while (board[Pair(adj, loc.second)] == null || board[Pair(adj, loc.second)] == ' ') {
                    adj = Math.floorMod(adj + 1, height)
                }
                if (board[Pair(adj, loc.second)] == '#') {
                    return
                }
                loc = Pair(adj, loc.second)
                moveForward(amount - 1)
            }

            2 -> {
                var adj = Math.floorMod(loc.second - 1, width)
                if (board[Pair(loc.first, adj)] == '#') {
                    return
                }
                while (board[Pair(loc.first, adj)] == null || board[Pair(loc.first, adj)] == ' ') {
                    adj = Math.floorMod(adj - 1, width)
                }
                if (board[Pair(loc.first, adj)] == '#') {
                    return
                }
                loc = Pair(loc.first, adj)
                moveForward(amount - 1)
            }

            3 -> {
                var adj = Math.floorMod(loc.first - 1, height)
                if (board[Pair(adj, loc.second)] == '#') {
                    return
                }
                while (board[Pair(adj, loc.second)] == null || board[Pair(adj, loc.second)] == ' ') {
                    adj = Math.floorMod(adj - 1, height)
                }
                if (board[Pair(adj, loc.second)] == '#') {
                    return
                }
                loc = Pair(adj, loc.second)
                moveForward(amount - 1)
            }

            else -> throw Exception()
        }
    }
}

// 175058 high
