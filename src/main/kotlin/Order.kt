import kotlinx.coroutines.*

class Order(private val menu: Menu, val number: Int) {

    private val dishes: MutableList<Dish> = mutableListOf()
    private var difficulty: Int = 0
    private lateinit var onPrepared: (Order)-> Unit
    private lateinit var job: Job
    var cost = 0
        private set

    suspend fun create(func: (Order) -> Unit) {
        onPrepared = func
        addDishes()
        startPreparing()
    }

    private suspend fun startPreparing() = coroutineScope {
        job = launch {
            delay(difficulty * 1000L)
        }
        onPrepared(this@Order)
    }

    fun show() = dishes.forEach { dish -> println(dish) }

    suspend fun delete() = job.cancelAndJoin()

    fun change() = addDishes()

    private fun addDishes() {
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
                cost += dish.cost
            } catch (e: IllegalArgumentException) {
                println(e.message)
            } catch (e: IndexOutOfBoundsException) {
                println("There is no opportunity to order this dish right now. Please, choose another dish")
            }
        }
    }


}