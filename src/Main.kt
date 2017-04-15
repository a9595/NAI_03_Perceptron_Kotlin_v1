import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by tieorange on 10/04/2017.
 */
class Main() {

    companion object {
        private val PATH_TRAINING = "iris_perceptron/training.txt"

        @JvmStatic fun main(args: Array<String>) {

            val weights = listOf<Double>(
                    -0.1,
                    0.20653640140000007,
                    -0.23418117710000003)

            val dataSet = listOf<List<Double>>(
                    listOf(2.7810836, 2.550537003, 0.0),
                    listOf(1.465489372, 2.362125076, 0.0),
                    listOf(3.396561688, 4.400293529, 0.0),
                    listOf(1.38807019, 1.850220317, 0.0),
                    listOf(3.06407232, 3.005305973, 0.0),
                    listOf(7.627531214, 2.759262235, 1.0),
                    listOf(5.332441248, 2.088626775, 1.0),
                    listOf(6.922596716, 1.77106367, 1.0),
                    listOf(8.675418651, -0.242068655, 1.0),
                    listOf(7.673756466, 3.508563011, 1.0)
            )

            for (row in dataSet) {
                val prediction = predict(row, weights)
                println("Expected = ${row.last()}, \t Predicted = $prediction ")
            }


            val learningRate: Double = 0.1
            val nEpoch = 5
            val weightsResult = trainWeights(dataSet, learningRate, nEpoch)
            printWeights(weightsResult)

            val list: List<List<Double>> = CsvReader.myRead(PATH_TRAINING)
        }

        fun perceptron(
                train: List<List<Double>>, test: List<List<Double>>,
                learningRate: Double, nEpoch: Int
        ): ArrayList<Double> {
            val predictions = ArrayList<Double>()
            val weights = trainWeights(train, learningRate, nEpoch)
            for (row in test) {
                val prediction: Double = predict(row, weights)
                predictions.add(prediction)
            }
            return predictions
        }

        fun evaluateAlgorithm(dataSet: List<List<Double>>, nFolds: Int) {
            val folds = crossValidationSplit(dataSet, nFolds)
            val scores = ArrayList<Double>()
            for (fold in folds) {
                val trainingSet = ArrayList<List<Double>>(folds)
                trainingSet.remove(fold)
                val trainingSetSum = trainingSet
                // trainingSet = trainingSet.sum() todo: uncomment
                for (row in fold) {

                }
            }
        }

        fun crossValidationSplit(dataSet: List<List<Double>>, nFolds: Int): ArrayList<List<Double>> {
            val dataSetSplit = ArrayList<List<Double>>()
            val dataSetCopy = ArrayList<List<Double>>(dataSet)
            val fold_size = (dataSet.size / nFolds)
            for (i in 0..nFolds) {
                val fold = ArrayList<List<Double>>()
                while (fold.size < fold_size) {
                    val index = Random().nextInt(dataSetCopy.size)
                    fold.add(dataSetCopy[index])
                }
                dataSetSplit.addAll(fold)
            }
            return dataSetSplit
        }

        private fun trainWeights(train: List<List<Double>>, learningRate: Double, nEpoch: Int): List<Double> {
            val weights = ArrayList<Double>(train[0].size) // TODO: find how to init array(size = 3)
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
            return weights.toList()
        }

        fun printWeights(weights: List<Double>) = weights.forEach { print("$it, ") }

        fun predict(row: List<Double>, weights: List<Double>): Double {
            var activation: Double = weights.first()
            for (i in 0..(row.size - 2)) {
                activation += weights[i + 1] * row[i]
            }
            return if (activation >= 0.0) 1.0 else 0.0
        }
    }
}