package addition

/**
 * Colors for checkers.
 * White and black respectively.
 */
enum class Color {
    WHITE,
    BLACK;

    /**
     * Returns the color of the enemy's checkers.
     */
    fun enemyColor() = if (this == WHITE) BLACK else WHITE

    /**
     * e.g. White instead of WHITE
     */
    override fun toString(): String = name.toLowerCase().capitalize()
}