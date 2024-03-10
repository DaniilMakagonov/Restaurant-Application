import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class Visitor : User() {
    private lateinit var onPayedOrder: (Int) -> Unit

    private val activeOrders = mutableMapOf<Int, Order>()

    private var numberForOrder = 1


    private fun makeOrder() : Order {
        val order = Order(menu, numberForOrder)
        menu.show()
        order.create(::onPreparedOrder)
        activeOrders[numberForOrder] = order
        numberForOrder++
        return order
    }

    private fun onPreparedOrder(order: Order) {
        payForOrder(order)
        activeOrders.remove(order.number)
    }

    private fun showActiveOrders() {
        if (activeOrders.isEmpty()) {
            println("There is no active order")
            return
        }
        activeOrders.values.forEach { order ->
            println("Number of order: ${order.number}")
            order.show()
            println()
        }
    }

    private suspend fun deleteOrder() {
        println("Input number of order")
        val number = readPositiveNumberOrNull()
        if (number == null) {
            println("Incorrect value")
            return
        }
        if (activeOrders.containsKey(number)) {
            activeOrders[number]!!.delete()
            activeOrders.remove(number)
        } else {
            println("Order with this number doesn't exist. Probably it was already finished or deleted")
        }
    }

    private fun changeOrder() {
        val number = readPositiveNumberOrNull()
        if (number == null) {
            println("Incorrect value")
            return
        }
        if (activeOrders.containsKey(number)) {
            activeOrders[number]!!.change()
        } else {
            println("Order with this number doesn't exist. Probably it was already finished or deleted")
        }
    }

    private fun payForOrder(order: Order) {
        println("Order number ${order.number} is ready.You payed ${order.cost}")
        onPayedOrder(order.cost)
    }

    override suspend fun work(menu: Menu) = coroutineScope{
        this@Visitor.menu = menu
        while (true) {
            println(
                """
            Choose action:
            - see menu
            - make order
            - show active orders
            - delete order
            - change order
            - exit
        """.trimIndent()
            )
            when (readln()) {
                "see menu" -> showMenu()
                "make order" -> {
                    val order = makeOrder()
                    println("Order starts to cooking")
                    order.job = launch {order.startPreparing() }
                }
                "show active orders" -> showActiveOrders()
                "delete order" -> deleteOrder()
                "change order" -> changeOrder()
                "exit" -> break
                else -> println("Incorrect input")
            }
        }
    }

    fun onPayedOrderInit(func: (Int) -> Unit) = run { onPayedOrder = func }
}