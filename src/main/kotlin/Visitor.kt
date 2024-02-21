import kotlinx.coroutines.*

class Visitor(
    var name: String,
    var login: String,
    private var password: String,
) {
    private val activeOrders = mutableListOf<Order>()
    private lateinit var menu : Menu
    private lateinit var updater : Job

    fun initMenu(menu : Menu) = run { this.menu = menu }

    suspend fun makeOrder() {
        val order = Order(menu)
        menu.show()
        order.create()
        activeOrders.add(order)
    }

    private suspend fun update() = coroutineScope {
        updater = launch {
            while (true) {
                activeOrders.removeIf { order -> !order.isActive() }
            }
        }
    }
}