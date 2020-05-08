package addition

open class Board {

    private val info = List(8) { MutableList<Checker?>(8) {null} }

    operator fun get(x:Int, y:Int) = info[x][y]
    operator fun set(x:Int, y:Int, value: Checker?) {
        require(x in 0..7 && y in 0..7)
        info[x][y] = value
        if (value is Checker) value.setBoard(this)
    }

    fun clear() {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                info[i][j] = null
            }
        }
    }


    fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        /*for (move in (info[x][y] ?: return result).getPossibleMoves(x, y).toMutableList()) {
            val piece = this[x, y]
            println("piece is $piece")
            println("pieces possible moves ${info[x][y]!!.getPossibleMoves(x, y).toMutableList()}")
            val otherPiece = this[move.first, move.second]
            this[move.first, move.second] = piece
            this[x, y] = null
            result.add(move)
            this[x, y] = piece
            this[move.first, move.second] = otherPiece
        }*/
        val piece = this[x, y]
        /*if (piece != null) {
            if (piece.canEat(x, y).first) {


            }
        }*/
        /*println("piece is $piece")
        println("pieces possible moves ${info[x][y]!!.getPossibleMoves(x, y).toMutableList()}")*/
        return (info[x][y] ?: return result).getPossibleMoves(x, y).toMutableList()
    }


    override fun hashCode(): Int = info.hashCode()

    override fun equals(other: Any?): Boolean = other is Board && other.info == this.info
}