
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class TextUrlReader( val pathName:String = "c:\temp\test.txt") {
//    val urls = mutableListOf<String>()  // the list can be in memory cached



    fun getSomeUrls(): Flow<String> = flow {
        val file = File(pathName)
        val reader = BufferedReader(withContext(Dispatchers.IO) {
            FileReader(file)
        })
        var line: String? = reader.readLine()
        while (line != null ) {
            emit(line)
            line = reader.readLine()
        }
        reader.close()
    }
        .flowOn(Dispatchers.IO)

}