/**
 * Created by tieorange on 10/04/2017.
 */
class Main() {

    companion object {
        @JvmStatic fun main(args: Array<String>) {

            val weights = doubleArrayOf(
                    -0.1,
                    0.20653640140000007,
                    -0.23418117710000003)

            val dataSet = arrayOf(
                    doubleArrayOf(2.7810836, 2.550537003, 0.0),
                    doubleArrayOf(1.465489372, 2.362125076, 0.0))

            for (row in dataSet) {
                val prediction = predict(row, weights)
                println("Expected = ${row.last()}, \t Predicted = $prediction ")
            }
        }

        fun predict(row: DoubleArray, weights: DoubleArray): Double {
            var activation: Double = weights.first()
            for (i in 0..row.size - 2) {
                activation += weights[i+1] * row[i]
            }
            return if(activation >= 0.0) 1.0 else 0.0
        }
    }
}