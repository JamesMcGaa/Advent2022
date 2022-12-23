import java.io.File

fun main() {
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


  while (!Monkey.ALL_MONKEYS["root"]!!.isFinalized) {
    Monkey.ALL_MONKEYS.values.forEach { monkey ->
      monkey.compute()
    }
    Monkey.ALL_MONKEYS.values.forEach { monkey ->
      monkey.finalize()
    }
  }
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

  fun compute() {
    if (!isFinalized) {
      if (ALL_MONKEYS[dependencies!!.first]!!.isFinalized &&
              ALL_MONKEYS[dependencies.second]!!.isFinalized
      ) {
        tempVal = when(opType) {
          "+" -> ALL_MONKEYS[dependencies.first]!!.literalVal!! + ALL_MONKEYS[dependencies.second]!!.literalVal!!
          "*" -> ALL_MONKEYS[dependencies.first]!!.literalVal!! * ALL_MONKEYS[dependencies.second]!!.literalVal!!
          "/" -> ALL_MONKEYS[dependencies.first]!!.literalVal!! / ALL_MONKEYS[dependencies.second]!!.literalVal!!
          "-" -> ALL_MONKEYS[dependencies.first]!!.literalVal!! - ALL_MONKEYS[dependencies.second]!!.literalVal!!
          else -> throw Exception("BAD OP")
        }
      }
    }
  }

  fun finalize() {
    if (!isFinalized && tempVal != null){
      isFinalized = true
      literalVal = tempVal
      if (ID == "root") {
        println(literalVal)
      }
    }
  }
}
