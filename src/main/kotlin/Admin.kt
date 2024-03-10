class Admin : User() {
    private lateinit var getIncome: () -> Int
    private fun addDish() {
        val dish = readDish()
        println("Input amount:")
        var amount = readPositiveNumberOrNull()
        while (amount == null) {
            println("Incorrect value")
            amount = readPositiveNumberOrNull()
        }
        try {
            menu.addPosition(dish, amount)
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }

    private fun removeDish() {
        println("Input name")
        try {
            menu.removePosition(readln())
        } catch (e: IllegalArgumentException) {
            println(e.message)
            return
        }
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

    override suspend fun work(menu: Menu) {
        this.menu = menu
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

    fun getIncomeInit(func: () -> Int) = run { getIncome = func }
}