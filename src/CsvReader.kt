import com.rubengees.mlp.invert
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object CsvReader {

    fun read(filename: String): CsvResult {
        val classMap = HashMap<String, Double>()
        var nextClassIndex = 0.0

        val inputStream = File("iris_perceptron/training.txt").inputStream()
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        val data: List<Pair<List<Double>, List<Double>>> = bufferedReader.readLines().map {
            val split = it.split(",").map<String, String>(transform = String::trim)

            if (split.size < 2) {
                null
            } else {
                try {
                    val

                            classToUse: Double = classMap.getOrPut(split.last(), {
                        val nextClassIndexToInsert = nextClassIndex

                        nextClassIndex++

                        nextClassIndexToInsert
                    })

                    val pair: Pair<List<Double>, List<Double>> = split.subList(0, split.size - 1).map<String, Double>(transform = String::toDouble).to(listOf(classToUse))
                    pair
                } catch (exception: NumberFormatException) {
                    null
                }
            }
        }.filterNotNull()

        return CsvResult(data, classMap.invert())
    }

    fun myRead(filename: String): List<List<Double>> {
        val inputStream = File(filename).inputStream()
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val data: List<List<Double>> = bufferedReader.readLines().map { line ->
            val columnsList: List<String> = line.split(",").map(String::trim)
            columnsList.subList(0, columnsList.size - 1).map(String::toDouble)
        }
        return data
    }

    class CsvResult(val data: List<Pair<List<Double>, List<Double>>>, val classMap: Map<Double, String>) {
        fun calculateMinMax(): Pair<Double, Double> {
            if (data.isEmpty()) {
                throw IllegalStateException("The data list cannot be empty")
            }

            val concatenatedValues = data.flatMap { it.first.plus(it.second) }

            return concatenatedValues.min()!! to concatenatedValues.max()!!
        }

        fun calculateInputAmount(): Int {
            if (data.isEmpty()) {
                throw IllegalStateException("The data list cannot be empty")
            }

            return data.first().first.size
        }

        fun calculateOutputAmount(): Int {
            return 1
        }

        fun mapToClass(value: List<Double>): String {
            if (classMap.isEmpty()) {
                throw IllegalStateException("The class map cannot be empty")
            }

            var bestMatch = Double.MIN_VALUE
            var lowestInaccuracy = Double.MAX_VALUE

            classMap.keys.forEach {
                val inaccuracy = Math.abs(Math.max(value.first(), it) - Math.min(value.first(), it))

                if (inaccuracy < lowestInaccuracy) {
                    lowestInaccuracy = inaccuracy
                    bestMatch = it
                }
            }

            return classMap.getOrElse(bestMatch, { throw IllegalStateException("No match found") })
        }
    }
}