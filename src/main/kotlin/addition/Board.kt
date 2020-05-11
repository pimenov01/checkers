package addition

open class Board {

    private val info = List(8) { MutableList<Checker?>(8) {null} }

    operator fun get(x:Int, y:Int) = info[x][y]
    operator fun set(x:Int, y:Int, value: Checker?) {
        require(x in 0..7 && y in 0..7)
        info[x][y] = value
        if (value is Checker) value.setBoard(this)
    }



    fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        return (info[x][y] ?: return result).getPossibleMoves(x, y).toMutableList()
    }


    override fun hashCode(): Int = info.hashCode()

    override fun equals(other: Any?): Boolean = other is Board && other.info == this.info
}