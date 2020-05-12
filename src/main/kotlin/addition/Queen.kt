package addition

/**
 * Extended Checker class with overridden functions.
 * Explanations of functions can be found in the Checker class.
 */
class Queen (color: Color): Checker(color) {

    override fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val enemyColor = if (this.color == Color.WHITE) Color.BLACK else Color.WHITE
        val result = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!

        if (canEat(x, y).first) return canEat(x, y).second

        for ((directionX, directionY) in listOf(-1 to 1, -1 to -1, 1 to 1, 1 to -1)) {
            var newX = x + directionX
            var newY = y + directionY
            while (newX in 0 until 8 && newY in 0 until 8 && isOpposite((board[newX, newY]))) {
                if ((board[newX, newY]) is Checker && board[newX, newY]?.color == enemyColor) {
                    break
                }
                result.add(Pair(newX, newY))
                newX += directionX
                newY += directionY
            }
        }
        return result
    }

    override fun canEat(x: Int, y: Int): Triple<Boolean, List<Pair<Int, Int>>, List<Pair<Int, Int>>> {
        var enemyCounter = 0
        val enemyCords = mutableListOf<Pair<Int, Int>>()
        val result = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!
        val list = listOf(-1 to 1, -1 to -1, 1 to 1, 1 to -1)

        for ((directionX, directionY) in list) {
            var newX = x + directionX
            var newY = y + directionY
            while (newX in 0 until 8 && newY in 0 until 8 && isOpposite(board[newX, newY])) {
                if (newX + directionX in 0 until 8 && newY + directionY in 0 until 8) {
                    if (board[newX, newY] is Checker && board[newX, newY]?.color != this.color
                            && board[newX + directionX, newY + directionY] is Checker && board[newX + directionX, newX + directionY]?.color != this.color) break
                    if (board[newX, newY] is Checker && board[newX, newY]?.color != this.color
                            && board[newX + directionX, newY + directionY] == null) {
                        enemyCounter++
                        if (enemyCounter > 1) break
                        enemyCords.add(newX to newY)

                    }
                    if (enemyCounter == 1) {
                        if (board[newX + directionX, newY + directionY] !is Checker)
                            result.add(newX + directionX to newY + directionY)
                    }
                }
                newX += directionX
                newY += directionY
            }
            enemyCounter = 0
        }
        return if (result.size != 0) Triple(true, result, enemyCords) else Triple(false, result, enemyCords)
    }

    override fun toString(): String = if (this.color == Color.WHITE) "white_queen" else "black_queen"
}