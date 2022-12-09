import java.io.File

fun main(args: Array<String>) {
    val board = mutableListOf<MutableList<Int>>()
    File("inputs/input8.txt").forEachLine { line ->
        board.add(line.split("").filter { ch -> ch.isNotBlank() }.map{ ch -> Integer.parseInt(ch)}.toMutableList())
    }
    var visibleCount = 0
    var maxScenicScore = 0
    val m = board.size
    val n = board[0].size

    for (r in 0 until m) {
        for (c in 0 until n) {
            val parallels = mutableListOf<MutableList<Int>>() // Should be sorted closest -> border elements
            val parallel = mutableListOf<Int>()


            for (row_val in 0 until r) {
                parallel.add(board[row_val][c])
            }
            parallel.reverse()
            parallels.add(ArrayList(parallel))
            parallel.clear()


            for (row_val in r+1 until m) {
                parallel.add(board[row_val][c])
            }
            parallels.add(ArrayList(parallel))
            parallel.clear()

            for (col_val in 0 until c) {
                parallel.add(board[r][col_val])
            }
            parallel.reverse()
            parallels.add(ArrayList(parallel))
            parallel.clear()


            for (col_val in c+1 until n) {
                parallel.add(board[r][col_val])
            }
            parallels.add(ArrayList(parallel))
            parallel.clear()

            var hasInc = false
            parallels.forEach { parallel ->
                if (hasInc) {
                    return@forEach
                }
                if (parallel.isEmpty() || board[r][c] > parallel.max()) {
                    visibleCount += 1

                }

            }

        }
    }

    println(visibleCount)
}



