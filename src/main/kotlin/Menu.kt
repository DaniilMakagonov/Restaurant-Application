import kotlinx.serialization.Serializable

@Serializable
class Menu(private val menu: MutableMap<Dish, Int>) {

    fun getDish(name: String): Dish = menu.keys.find { dish -> dish.name == name }
        ?: throw IllegalArgumentException("There is no position with this name in menu")

    fun addPosition(dish: Dish, amount: Int = 1) {
        if (menu.filterKeys { it.name == dish.name }.isEmpty()) {
            menu[dish] = amount
        } else {
            throw IllegalArgumentException("This dish is already in menu")
        }
    }

    fun removePosition(dishName: String) = menu.remove(getDish(dishName))

    fun setAmount(dish: Dish, amount: Int) {
        if (menu.filterKeys { it.name == dish.name }.isEmpty()) {
            throw IllegalArgumentException("There is no position with this name in menu")
        }
        if (amount < 0) {
            throw IndexOutOfBoundsException("Incorrect amount, it should be non-negative")
        }
        menu[dish] = amount
    }

    fun addToOrder(dishName: String): Dish {
        val dish = getDish(dishName)
        setAmount(dish, menu[dish] as Int - 1)
        return dish
    }

    fun show() {
        if (menu.isEmpty()) {
            println("Menu is empty")
            return
        }
        menu.forEach { (dish, amount) -> println("$dish, $amount") }
    }

}