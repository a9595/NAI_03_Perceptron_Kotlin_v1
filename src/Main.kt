import java.util.EnumSet.range
import java.util.stream.IntStream.range
import java.util.stream.LongStream.range

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
                    doubleArrayOf(1.465489372, 2.362125076, 0.0),
                    doubleArrayOf(3.396561688, 4.400293529, 0.0),
                    doubleArrayOf(1.38807019, 1.850220317, 0.0),
                    doubleArrayOf(3.06407232, 3.005305973, 0.0),
                    doubleArrayOf(7.627531214,2.759262235,1.0),
                    doubleArrayOf(5.332441248,2.088626775,1.0),
                    doubleArrayOf(6.922596716,1.77106367,1.0),
                    doubleArrayOf(8.675418651,-0.242068655,1.0),
                    doubleArrayOf(7.673756466,3.508563011,1.0)
                    )

            for (row in dataSet) {
                val prediction = predict(row, weights)
                println("Expected = ${row.last()}, \t Predicted = $prediction ")
            }


            val learningRate:Double = 0.1
            val nEpoch = 5
            val weightsResult = trainWeights(dataSet, learningRate, nEpoch)
            printWeights(weightsResult)
        }

        private fun trainWeights(train: Array<DoubleArray>, learningRate: Double, nEpoch: Int): DoubleArray {
            val weights = doubleArrayOf(0.0, 0.0, 0.0) // TODO: find how to init array(size = 3)
            for (epoch in 0..nEpoch) {
                var sumError = 0.0
                var error = 0.0
                for (row in train) {
                    val prediction = predict(row, weights)
                    error = row.last() - prediction
                    sumError += Math.pow(error, 2.0)
                    weights[0] = weights[0] + learningRate * error
                    for (i in 0..(row.size - 2))
                        weights[i + 1] = weights[i + 1] + learningRate * error * row[i]
                }
                println(">epoch = $epoch, lRate = $learningRate, error = $sumError")
            }
            return weights
        }

        fun printWeights(weights: DoubleArray){
            weights.forEach { print("$it, ") }
        }

        fun predict(row: DoubleArray, weights: DoubleArray): Double {
            var activation: Double = weights.first()
            for (i in 0..(row.size - 2)) {
                activation += weights[i + 1] * row[i]
            }
            return if (activation >= 0.0) 1.0 else 0.0
        }
    }
}