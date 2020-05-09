package addition

class Queen (color: Color): Checker(color) {

    override fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!
        for ((directionX, directionY) in listOf(-1 to 1, -1 to -1, 1 to 1, 1 to -1)) {
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
    }

    override fun toString(): String = if (this.color == Color.WHITE) "white_queen" else "black_queen"

}