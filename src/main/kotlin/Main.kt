import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors
// (c) Salman ALim Marvasti
//val concurrentMap = java.util.ConcurrentHashMap<String, Int>()
var dispatcher: ExecutorCoroutineDispatcher = Executors.newFixedThreadPool(4).asCoroutineDispatcher()
var reader: TextUrlReader = TextUrlReader()

val client = HttpClient() {
    install(HttpTimeout) {
        requestTimeoutMillis = 5000
        connectTimeoutMillis = 5000
        socketTimeoutMillis = 5000
    }
}


// Todo use the ktor client on the seaprate process actors and then map reduce the results
suspend fun groupAndHandOverToActor(v: String): String {
    val httpResponse: HttpResponse = client.get(v) {
//        onDownload { bytesSentTotal, contentLength ->
//            bytesSentTotal + contentLength
//        }
    }
    val responseBody: String = Hasher.getMD5Checksum(httpResponse.bodyAsChannel().toInputStream())
//    println("Thread ${Thread.currentThread().name}")
    return responseBody
}


fun process(flow: Flow<String>): Flow<String> {
    return flow
        .map { CoroutineScope(dispatcher).async { groupAndHandOverToActor(it) } }
        .buffer()
        .map { it.await() } // results sequentially collected from each thread/coroutine
        .flowOn(dispatcher)
}


suspend fun main(args: Array<String>) {

    if (args.size < 2) {
        println("Please provide number of threads and a file path e.g. 4 c:\\temp\\urls")
        return
    } else {
        runBlocking {
            dispatcher = Executors.newFixedThreadPool(args[0].toInt()).asCoroutineDispatcher()
            reader = TextUrlReader(args[1])
        }
    }
    process(reader.getSomeUrls()).collect { println(it) }
    // runBlocking { launch{ reader.testFlows() } }
    println("Program arguments: ${args.joinToString()}")
    dispatcher.close()
}