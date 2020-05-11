package addition

enum class Color {
    WHITE,
    BLACK;

    fun enemyColor() = if (this == WHITE) BLACK else WHITE

    override fun toString(): String = name.toLowerCase().capitalize()

}