import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileReader
import java.io.FileWriter

@Serializable
class System {
    @Transient
    private lateinit var json: Json
    private var income = 0
    private val menu = Menu(mutableMapOf())

    private fun addUser(login: String, fileName: String) =
        FileWriter(fileName, true).write("$login\n")

    private fun isUserRegister (login: String, fileName: String) : Boolean =
        FileReader(fileName).readLines().contains("$login\n")

    private suspend fun loginUser() {
        val loginInfo = getLoginInfo()
        val type = loginInfo[0]
        val login = loginInfo[1]
        if (!isUserRegister(login, "$type.txt")) {
            println("Incorrect login or password")
            return
        }
        val user = json.decodeFromString<User>(withContext(Dispatchers.IO) {
            FileReader("$login.txt").readText()
        })
        user.work()
        withContext(Dispatchers.IO) {
            FileWriter("$login.txt").write(json.encodeToString(user))
        }
    }

    private fun registerUser() {
        val loginInfo = getLoginInfo()
        val type = loginInfo[0]
        val login = loginInfo[1]
        if (isUserRegister(login, "$type.txt")) {
            println("User with this login already exist")
            return
        }
        val user = if (type == "admin") Admin(login) { return@Admin income } else Visitor(login, ::onPayedOrder)
        user.initMenu(menu)
        FileWriter("$login.txt").write(json.encodeToString(user))
        addUser(login, "$type.txt")
        println("Registration was successful. You may login")
    }

    private fun getLoginInfo() : List<String>{
        println("Input user type (admin or visitor):")
        var type = readln()
        while (type != "admin" && type != "visitor") {
            println("Incorrect input, repeat please")
            type = readln()
        }
        println("Input login:")
        val login = readln()
        return listOf(type, login)
    }

    suspend fun work(json: Json) {
        this.json = json
        while (true) {
            println("""
                Choose option:
                - login
                - register
                - exit
            """.trimIndent())
            when (readln()) {
                "login" -> loginUser()
                "register" -> registerUser()
                "exit" -> break
                else -> println("Incorrect input")
            }
        }
    }

    private fun onPayedOrder(orderCost : Int) {
        income += orderCost
    }
}