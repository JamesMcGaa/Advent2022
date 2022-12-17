import java.io.File
import kotlin.Exception
import kotlin.math.absoluteValue

fun main() {

    val beacons = mutableListOf<Pair<Int, Int>>()
    val sensors = mutableListOf<Pair<Int, Int>>()
    val covered: HashSet<Pair<Int,Int>> = hashSetOf()

    val lines = File("inputs/input15.txt").forEachLine {
        line ->
        val split = line.split(":")
        val sensorInput = split[0]
        val beaconInput = split[1]

        val xSensor = sensorInput.split(",")[0]
        val xStart = xSensor.indexOf("=") + 1
        val xSensorVal = Integer.parseInt(xSensor.substring(xStart))

        val ySensor = sensorInput.split(",")[1]
        val ySensorVal = Integer.parseInt(ySensor.substring(3))
        val sensor = Pair(xSensorVal, ySensorVal)
        sensors.add(sensor)

        val xBeacon = beaconInput.split(",")[0]
        val xBeaconStart = xBeacon.indexOf("=") + 1
        val xBeaconVal = Integer.parseInt(xBeacon.substring(xBeaconStart))

        val yBeacon = beaconInput.split(",")[1]
        val yBeaconVal = Integer.parseInt(yBeacon.substring(3))
        val beacon = Pair(xBeaconVal, yBeaconVal)
        beacons.add(beacon)


        val beaconDist = (xSensorVal-xBeaconVal).absoluteValue + (ySensorVal-yBeaconVal).absoluteValue
        val yDist = (ySensorVal - 2000000).absoluteValue
        val leftoverBudgetForX = beaconDist - yDist
        if (leftoverBudgetForX >= 0) {
            for (i in xSensorVal - leftoverBudgetForX .. xSensorVal + leftoverBudgetForX) {
                val potentialPair = Pair(i,2000000)
                if (potentialPair != beacon && potentialPair != sensor && !covered.contains(potentialPair)) {
                    covered.add(potentialPair)
                }
            }
        }
        println(covered.size)
    }
}

 
