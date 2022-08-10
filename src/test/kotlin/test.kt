import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class test {
    suspend fun TextUrlReader.testFlows() =
        withContext(dispatcher) {
            // By having a buffered flow we can send the large file urls to different worker on different threads
            // via Actors communication queue
            val time = measureTimeMillis {
                getSomeUrls().buffer().onCompletion { println("Flow processing completed") }.collect { value ->
                    val v = groupAndHandOverToActor(value)
//                print("Thread: ${Thread.currentThread().name}")
                    println(" ${v}")
                    v

                }
            }
            println("Time taken: $time")
        }
}