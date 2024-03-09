import kotlinx.serialization.Transient

class Visitor(
    name: String,
    login: String,
    password: String,
) : User(name, login, password) {
    @Transient
    private val activeOrders = mutableMapOf<Int, Order>()

    @Transient
    private var numberForOrder = 1


    private suspend fun makeOrder() {
        val order = Order(menu, numberForOrder)
        menu.show()
        order.create(::onPreparedOrder)
        activeOrders[numberForOrder] = order
        numberForOrder++
    }

     private fun onPreparedOrder(order: Order) {
        payForOrder(order)
        activeOrders.remove(order.number)
    }

     private fun showActiveOrders() {
        activeOrders.values.forEach { order ->
            println("Number of order: ${order.number}")
            order.show()
            println()
        }
    }

     private suspend fun deleteOrder() {
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
        println("You payed ${order.cost}. Press Enter to continue")
        readln()
    }

    override suspend fun work() {
        while (true) {
            println("""
            Choose action:
            - see menu
            - make order
            - show active orders
            - delete order
            - change order
            - exit
        """.trimIndent())
            when (readln()) {
                "see menu" -> showMenu()
                "make order" -> makeOrder()
                "show active orders" -> showActiveOrders()
                "delete order" -> deleteOrder()
                "change order" -> changeOrder()
                "exit" -> return
                else -> println("Incorrect input")
            }
        }
    }
}