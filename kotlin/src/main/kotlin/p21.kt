import java.io.File
import java.util.Scanner

fun main() {
  println("STARTED")
  var step = 1000000000000L
  var current = 0L
  while(true) {
    println("--------${current}---------")
    current += step
    Monkey.ALL_MONKEYS.clear()
    val result = run(current, false)!!
    if (result.first - result.second < 0) {
      current -= step
      step /= 10L
    }
    if (result.first - result.second == 0L) {
      println(result)
      println("DONE")
      break
    }
  }

  for (i in current - 1000 .. current + 1000) {
    println(i)
    val result = run(i, true)
    if (result != null && result.first - result.second == 0L) {
      println(i)
      return
    }
  }
}

fun run(trial: Long, strictDivision: Boolean): Pair<Long, Long>? {
  File("input21.txt").forEachLine { line ->
    val identifier = line.split(":")[0].trim()
    val op = line.split(":")[1]
    when {
      op.contains("-") ->
          Monkey(
              identifier,
              null,
              Pair(op.split("-").map { it.trim() }[0], op.split("-").map { it.trim() }[1]),
              "-",
              false
          )
      op.contains("+") ->
          Monkey(
              identifier,
              null,
              Pair(op.split("+").map { it.trim() }[0], op.split("+").map { it.trim() }[1]),
              "+",
              false
          )
      op.contains("/") ->
          Monkey(
              identifier,
              null,
              Pair(op.split("/").map { it.trim() }[0], op.split("/").map { it.trim() }[1]),
              "/",
              false
          )
      op.contains("*") ->
          Monkey(
              identifier,
              null,
              Pair(op.split("*").map { it.trim() }[0], op.split("*").map { it.trim() }[1]),
              "*",
              false
          )
      else -> Monkey(identifier, Integer.parseInt(op.trim()).toLong(), null, null, true)
    }
  }
  Monkey.ALL_MONKEYS["humn"]!!.literalVal = trial

  while (!Monkey.ALL_MONKEYS["root"]!!.isFinalized) {
    Monkey.ALL_MONKEYS.values.forEach { monkey ->
      val computeVal = monkey.compute(strictDivision)
      if (computeVal == null && strictDivision) {
        return null
      }
      if (monkey.ID == "root" && monkey.isFinalized) {
        return computeVal
      }
    }
    Monkey.ALL_MONKEYS.values.forEach { monkey ->
      monkey.finalize()
    }
  }

  return null
}

class Monkey(
    val ID: String,
    var literalVal: Long?,
    val dependencies: Pair<String, String>?,
    val opType: String?,
    var isFinalized: Boolean,
) {
  var tempVal: Long? = null
  init {
    ALL_MONKEYS.put(ID, this)
  }

  companion object {
    val ALL_MONKEYS = hashMapOf<String, Monkey>()
  }

  fun compute(strictDivision: Boolean): Pair<Long, Long>? {
    if (!isFinalized) {
      if (ALL_MONKEYS[dependencies!!.first]!!.isFinalized &&
              ALL_MONKEYS[dependencies.second]!!.isFinalized
      ) {
        tempVal = when(opType) {
          "+" -> ALL_MONKEYS[dependencies.first]!!.literalVal!! + ALL_MONKEYS[dependencies.second]!!.literalVal!!
          "*" -> ALL_MONKEYS[dependencies.first]!!.literalVal!! * ALL_MONKEYS[dependencies.second]!!.literalVal!!
          "/" -> {
            if (strictDivision && ALL_MONKEYS[dependencies.first]!!.literalVal!! % ALL_MONKEYS[dependencies.second]!!.literalVal!! != 0L) {
              return null
            }
            ALL_MONKEYS[dependencies.first]!!.literalVal!! / ALL_MONKEYS[dependencies.second]!!.literalVal!!
          }

          "-" -> ALL_MONKEYS[dependencies.first]!!.literalVal!! - ALL_MONKEYS[dependencies.second]!!.literalVal!!
          else -> throw Exception("BAD OP")
        }

        if (ID == "root") {
          isFinalized = true
          println(ALL_MONKEYS[dependencies.first]!!.literalVal!!)
          println(ALL_MONKEYS[dependencies.second]!!.literalVal!!)
          println(ALL_MONKEYS[dependencies.first]!!.literalVal!! - ALL_MONKEYS[dependencies.second]!!.literalVal!!)
          return Pair(ALL_MONKEYS[dependencies.first]!!.literalVal!!, ALL_MONKEYS[dependencies.second]!!.literalVal!!)
        }
      }
    }
    return Pair(-100L, -1000L)
  }

  fun finalize() {
    if (!isFinalized && tempVal != null){
      isFinalized = true
      literalVal = tempVal
    }
  }
}
