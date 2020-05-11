package addition


open class Checker(val color: Color) {
    private var board: Board? = null
    fun getBoard() = board
    fun isOpposite(other: Checker?) = (other?.color ?: false) != this.color
    fun setBoard(board: Board) {
        this.board = board
    }

    open fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        if (canEat(x, y).first) return canEat(x, y).second
        val list = if (this.color == Color.WHITE) listOf(-1 to 1, -1 to -1) else listOf(1 to -1, 1 to 1)
        val result = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!
        for ((directionX, directionY) in list) {
            val newX = x + directionX
            val newY = y + directionY
            if (newX in 0 until 8 && newY in 0 until 8 && isOpposite(board[newX, newY])) {
                if (board[newX, newY] !is Checker)
                result.add(Pair(newX, newY))
            }
        }

        return result
    }

    open fun canEat(x: Int, y: Int): Triple<Boolean, List<Pair<Int, Int>>, List<Pair<Int, Int>>> { // List<Pair<Int, Int>> // Pair<Boolean, List<Pair<Int, Int>>> //Triple<Boolean, List<Pair<Int, Int>>, Boolean>
        val result = mutableListOf<Pair<Int, Int>>()
        val enemyCords = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!
        val list = listOf(-1 to 1, -1 to -1, 1 to 1, 1 to -1)

        for ((newX, newY) in list) {
            if (x + 2 * newX in 0 until 8 && y + 2 * newY in 0 until 8) {

                if (board[x + newX, y + newY] is Checker && this.color != board[x + newX, y + newY]?.color
                        && board[x + 2 * newX, y + 2 * newY] == null) {
                    result.add(x + 2 * newX to y + 2 * newY)
                    enemyCords.add(x + newX to y + newY)
                }
            }
        }

        return if (result.size != 0) Triple(true, result, enemyCords) else Triple(false, result, enemyCords)

    }



    override fun toString(): String = if (this.color == Color.WHITE) "white_checker" else "black_checker"


    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + (board?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Checker

        if (color != other.color) return false

        return true
    }


}