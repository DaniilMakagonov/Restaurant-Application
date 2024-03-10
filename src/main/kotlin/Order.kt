import kotlinx.coroutines.*

class Order(private val menu: Menu, val number: Int) {

    private val dishes: MutableList<Dish> = mutableListOf()
    private var difficulty: Int = 0
    private lateinit var onPrepared: (Order)-> Unit
    lateinit var job: Job
    var cost = 0
        private set

    fun create(func: (Order) -> Unit) {
        onPrepared = func
        addDishes()
    }

    suspend fun startPreparing() {
        delay(difficulty * 100L)
        onPrepared(this@Order)
    }

    fun show() = dishes.forEach { dish -> println(dish) }

    suspend fun delete() = job.cancelAndJoin()

    fun change() = addDishes()

    private fun addDishes() {
        while (true) {
            println("Choose available dishes from menu or input \"exit\" for send it")
            val command = readln()
            if (command == "exit") {
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