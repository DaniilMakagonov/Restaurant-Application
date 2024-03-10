import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import java.io.FileReader
import java.io.File

@Serializable
class System {
    @Transient
    private lateinit var json: Json
    private var income = 0
    private val menu = Menu(mutableMapOf())

    init {
        val admin = File("admin.txt")
        val visitor = File("visitor.txt")
        if (!admin.exists()) {
            admin.createNewFile()
        }
        if (!visitor.exists()) {
            visitor.createNewFile()
        }
    }

    private fun addUser(login: String, fileName: String) =
        File(fileName).appendText("$login\n")

    private fun isUserRegister (login: String, fileName: String) : Boolean =
        FileReader(fileName).readLines().contains(login)

    private suspend fun loginUser() {
        val loginInfo = getLoginInfo()
        val type = loginInfo[0]
        val login = loginInfo[1]
        if (!isUserRegister(login, "$type.txt")) {
            println("Incorrect login")
            return
        }

        if (type == "admin") {
            val admin = Admin()
            admin.getIncomeInit { return@getIncomeInit income }
            admin.work(menu)
        } else {
            val visitor = Visitor()
            visitor.onPayedOrderInit(::onPayedOrder)
            visitor.work(menu)
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