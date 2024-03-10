suspend fun main() {
    val controller = Controller()
    try {
        controller.start()
    } catch(e: Exception) {
        println(e.message)
        println(e.stackTrace)
    } finally {
        controller.exit()
    }
}
