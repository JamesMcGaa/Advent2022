import java.io.File

fun main() {
    var counter = 0
    File("src/input25.txt").forEachLine {
        counter += SNAFUstringToDecimalInt(it)
    }
    println(counter)
    println(decimalIntToSnafuString(counter))
}

fun SNAFUstringToDecimalInt(str: String): Int {
    var counter = 0
    str.forEach { ch ->
        counter *= 5
        when (ch) {
            '2' -> counter += 2
            '1' -> counter += 1
            '0' -> counter += 0
            '-' -> counter -= 1
            '=' -> counter -= 2
            else -> throw Exception()
        }
    }
    return counter
}

fun decimalIntToSnafuString(dec: Int): String {
    val num = mutableListOf<String>("1")
    var decEquivalent = 1
    while (2 * decEquivalent < dec) {
        num.add("0")
        decEquivalent *= 5
    }
    val numDigits = num.size // simplify the above
    var addStr = ""
    for (i in 1..numDigits) {
        addStr += 2
    }
    val adjDec = dec + Integer.parseInt(addStr, 5)
    val strBase5 = Integer.toString(adjDec, 5)
    var transformed = ""
    strBase5.forEach {
        transformed += when(it) {
            '4' -> '2'
            '3' -> '1'
            '2' -> '0'
            '1' -> '-'
            '0' -> '='
            else -> throw Exception()
        }
    }
    return transformed
}
