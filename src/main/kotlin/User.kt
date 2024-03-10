sealed class User {
    protected lateinit var menu: Menu

    protected fun showMenu() = menu.show()

    abstract suspend fun work(menu: Menu)

    protected fun readPositiveNumberOrNull(): Int? {
        var number = readln().toIntOrNull()
        if (number != null && number <= 0) {
            number = null
        }
        return number
    }
}