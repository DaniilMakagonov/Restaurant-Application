suspend fun main() {
    val controller = Controller()
    try {
        controller.start()
    } catch(e: Exception) {
        println(e.message)
        println(e.stackTrace.toString())
        println("Send message with the description of error to developer:\ndamakagonov@edu.hse.ru")
    } finally {
        controller.exit()
    }
}
