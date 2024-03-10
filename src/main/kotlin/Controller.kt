import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class Controller {
    private val systemFile = File("system.txt")
    private lateinit var system: System
    private val json = Json { allowStructuredMapKeys = true }

    suspend fun start() {
        system = if (systemFile.exists()) {
            json.decodeFromString(withContext(Dispatchers.IO) {
                systemFile.readText()
            })
        } else {
            withContext(Dispatchers.IO) {
                systemFile.createNewFile()
            }
            System()
        }
        println("Welcome!")
        system.work(json)
    }

    fun exit() {
        systemFile.writeText(json.encodeToString(system))
    }
}