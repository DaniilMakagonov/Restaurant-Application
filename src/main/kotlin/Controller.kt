import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class Controller {
    private val systemFilePath = "system.txt"
    private lateinit var system: System
    private val json = Json {allowStructuredMapKeys = true}

    suspend fun start() {
        system = if (File(systemFilePath).exists()) {
            json.decodeFromString(withContext(Dispatchers.IO) {
                FileReader(systemFilePath).readText()
            })
        } else {
            System()
        }
        system.work(json)
    }
    fun exit() {
        FileWriter(systemFilePath).write(Json.encodeToString(system))
    }
}