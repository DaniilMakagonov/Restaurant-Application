import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class User {
    @Transient
    protected lateinit var menu: Menu

    fun initMenu(menu: Menu) = run { this.menu = menu }


    protected fun showMenu() = menu.show()

    abstract suspend fun work()

    protected fun readPositiveNumberOrNull() : Int? {
        var number = readln().toIntOrNull()
        if (number != null && number <= 0) {
            number = null
        }
        return number
    }
}