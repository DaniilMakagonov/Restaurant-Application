class Admin(
    private val getIncome: () -> Int
) : User() {

    private fun addDish() = menu.addPosition(readDish())

    private fun removeDish() {
        println("Input name")
        menu.removePosition(readln())
    }

    private fun changeDish() {
        println("Input name")
        val dish: Dish
        try {
            dish = menu.getDish(readln())
        } catch (e: IllegalArgumentException) {
            println(e.message)
            return
        }
        while (true) {
            println(
                """
                Input property yoy want to change:
                - amount
                - cost
                - difficulty
                Input "save" for save changes
                """.trimIndent()
            )

            val text = readln()
            when (text) {
                "amount" -> changeDishAmount(dish)
                "cost" -> changeDishCost(dish)
                "difficulty" -> changeDishDifficulty(dish)
                "save" -> break
                else -> println("Incorrect input")
            }
        }
    }

    private fun changeDishDifficulty(dish: Dish) {
        println("Input new difficulty:")
        val difficulty = readPositiveNumberOrNull()
        if (difficulty == null) {
            println("Incorrect value")
        } else {
            dish.difficulty = difficulty
        }
    }

    private fun changeDishAmount(dish: Dish) {
        println("Input new amount:")
        val amount = readPositiveNumberOrNull()
        if (amount == null) {
            println("Incorrect value")
        } else {
            menu.setAmount(dish, amount)
        }
    }

    private fun changeDishCost(dish: Dish) {
        println("Input new cost:")
        val cost = readPositiveNumberOrNull()
        if (cost == null) {
            println("Incorrect value")
        } else {
            dish.cost = cost
        }
    }

    override suspend fun work() {
        while (true) {
            println(
                """
            Choose action:
            - see menu
            - add dish
            - remove dish
            - change dish
            - see actual income
            - exit
        """.trimIndent()
            )
            when (readln()) {
                "see menu" -> showMenu()
                "add dish" -> addDish()
                "remove dish" -> removeDish()
                "change dish" -> changeDish()
                "see actual income" -> println(getIncome())
                "exit" -> return
                else -> println("Incorrect input")
            }
        }
    }

    private fun readDish(): Dish {
        println("Input name:")
        val name = readln()
        println("Input cost:")
        var cost = readPositiveNumberOrNull()
        while (cost == null) {
            println("Incorrect value, repeat please")
            cost = readPositiveNumberOrNull()
        }
        println("Input difficulty:")
        var difficulty = readPositiveNumberOrNull()
        while (difficulty == null) {
            println("Incorrect value, repeat please")
            difficulty = readPositiveNumberOrNull()
        }
        return Dish(name, cost, difficulty)
    }
}