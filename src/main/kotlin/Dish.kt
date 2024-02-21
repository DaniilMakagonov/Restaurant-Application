import kotlinx.serialization.Serializable

@Serializable
data class Dish(
    val name: String,
    var cost: Int,
    var difficulty: Int
) {
    override fun toString(): String {
        return "$name - $cost"
    }
}