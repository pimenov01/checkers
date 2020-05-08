package addition


class Checker(val color: Color) {
    private var board: Board? = null
    private fun getBoard() = board
    private fun isOpposite(other: Checker?) = (other?.color ?: false) != this.color
    fun setBoard(board: Board) {
        this.board = board
    }
    
    /**
     * В разработке для ферзя
     */
    /*fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!
        //for ((directionX, directionY) in listOf(1 to 1, 1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1, 0 to 1)) {
        for ((directionX, directionY) in listOf(-1 to 1, -1 to -1*//*, -1 to 1, 1 to -1*//*)) {
            var newX = x + directionX
            var newY = y + directionY
            while (newX in 0 until 8 && newY in 0 until 8 && isOpposite((board[newX, newY]))) {
                result.add(Pair(newX, newY))
                if ((board[newX, newY]) is Checker) {
                    break
                }
                newX += directionX
                newY += directionY
            }
        }
        return result
    }*/
    /**
     * Needed to be fixed.
     */
    fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        /*println("coords $x, $y")
        println("canEat? ${canEat(x, y)}")*/
        //if ()
        //if (board?.get(x, y) == null) return mutableListOf()
        //while (isCheck()) return canEat(x, y).second //Допилить невозможность передачи хода когда есть возможность брать
        if (canEat(x, y).first) return canEat(x, y).second
        val list = if (this.color == Color.WHITE) listOf(-1 to 1, -1 to -1) else listOf(1 to -1, 1 to 1)
        val result = mutableListOf<Pair<Int, Int>>()

        val board = this.getBoard()!!
        for ((directionX, directionY) in list) {
            val newX = x + directionX
            val newY = y + directionY
            if (newX in 0 until 8 && newY in 0 until 8 && isOpposite(board[newX, newY])) {
                result.add(Pair(newX, newY))
            }
        }

        return result
    }

    fun canEat(x: Int, y: Int): Pair<Boolean, List<Pair<Int, Int>>> { // List<Pair<Int, Int>> // Pair<Boolean, List<Pair<Int, Int>>> //Triple<Boolean, List<Pair<Int, Int>>, Boolean>
        val result = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!
        val enemy = if(this.color == Color.WHITE) Checker(Color.BLACK) else Checker(Color.WHITE)
        //println("enemy $enemy")
        val list = if (this.color == Color.WHITE) listOf(-1 to 1, -1 to -1) else listOf(1 to -1, 1 to 1)

        for ((newX, newY) in list) {
            /*println("${x + 2 * newX}")
            println("${y + 2 * newY}")*/
            if (x + 2 * newX in 0 until 8 && y + 2 * newY in 0 until 8) /*return (false to result)*/ /*break*/ {

                /*println("enemy is near? ${board[x + newX, y + newY] == enemy}")
                println("empty behind enemy? ${board[x + 2 * newX, y + 2 * newY] == null}")*/

                if (board[x + newX, y + newY] == enemy && board[x + 2 * newX, y + 2 * newY] == null) result.add(x + 2 * newX to y + 2 * newY)//return true
            }
        }
        /*println("if can it, then tp to cords $result")
        println("123465")*/
        return if (result.size != 0) true to result else false to result
    }

     /*private fun isCheck(): Boolean {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (board?.get(i, j) is Checker && board!![i, j]!!.canEat(i, j).first) {
                    return true
                }
            }
        }
        return false
    }*/


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
        //if (board != other.board) return false

        return true
    }


}