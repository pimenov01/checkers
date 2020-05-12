package addition

/**
 * A Board class that is supplemented by the DeskGUI class.
 */
open class Board {
    private val info = List(8) { MutableList<Checker?>(8) {null} }

    operator fun get(x:Int, y:Int) = info[x][y]
    operator fun set(x:Int, y:Int, value: Checker?) {
        require(x in 0..7 && y in 0..7)
        info[x][y] = value
        if (value is Checker) value.setBoard(this)
    }

    /**
     * Returns all possible moves.
     * Maybe I should rewrite this function,
     * but it works so I don't touch it.
     */
    fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        return (info[x][y] ?: return result).getPossibleMoves(x, y)
    }

    override fun hashCode(): Int = info.hashCode()

    override fun equals(other: Any?): Boolean = other is Board && other.info == this.info
}