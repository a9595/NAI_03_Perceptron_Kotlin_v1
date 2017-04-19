import Main.Companion.accuracyMetric
import org.junit.jupiter.api.Assertions.*

/**
 * Created by andrii.kovalchuk on 19/04/2017.
 */
internal class MainTest {
    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.Test
    fun main() {
        val accuracyMetric = accuracyMetric(listOf(1.0, 1.0, 3.0, 3.0), listOf(1.0, 1.0, 5.0, 5.0))
        assertEquals(50.0f, accuracyMetric)
        print(accuracyMetric)
    }

}