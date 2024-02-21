import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Order (private val menu : Menu) {

    private val dishes : MutableList<Dish> = mutableListOf()
    private var difficulty : Int = 0
    private lateinit var job : Job


    suspend fun create() {
        while (true) {
            println("Choose available dishes from menu or input \"Exit\" for send it")
            val command = readln()
            if (command == "Exit") {
                break
            }
            try {
               val dish = menu.addToOrder(command)
                dishes.add(dish)
                difficulty += dish.difficulty
            } catch (e : IllegalArgumentException) {
                println(e.message)
            } catch (e : IndexOutOfBoundsException) {
                println("There is no opportunity to order this dish right now. Please? choose another dish")
            }
        }
        startPreparing()
    }

    private suspend fun startPreparing() = coroutineScope {
        job = launch {
            delay(difficulty * 1000L)
        }
    }

    fun isActive() = job.isActive
}