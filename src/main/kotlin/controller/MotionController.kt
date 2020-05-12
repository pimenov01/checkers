package controller

import addition.Checker
import addition.Color
import main.app.DeskGUI

/**
 * Class responsible for the movement of checkers on the field.
 */
class MotionController {
    private lateinit var desk: DeskGUI
    var turn = Color.WHITE

    fun setDeskPointer (deskGUI: DeskGUI) {
        desk = deskGUI
    }

    /**
     * Move a checker from one cell to another.
     * Briefly: if a checker can't eat from its cell and the cell it wants to go to is available to it,
     * it goes there and the move is passed to the opponent.
     * If a checker can eat, it must eat. The enemy is removed from the field.
     * If the checker can no longer eat from the new cell, the move is passed to the opponent.
     */
    fun handle (fromRow: Int, fromColumn: Int, toRow: Int?, toColumn: Int?) {

        if (toRow == null || toColumn == null) {
            return
        }

        if (desk[fromRow, fromColumn] is Checker && desk[fromRow, fromColumn]!!.color == turn) {
            if (fromRow to fromColumn in eatAndMove()) {
                if (!desk[fromRow, fromColumn]?.canEat(fromRow, fromColumn)?.first!!) {
                    if (toRow to toColumn in desk.getPossibleMoves(fromRow, fromColumn)) {
                        desk.move(fromRow, fromColumn, toRow, toColumn)
                        turn = turn.enemyColor()
                    }
                } else {
                    if (toRow to toColumn in desk[fromRow, fromColumn]?.canEat(fromRow, fromColumn)?.second!!) {
                        desk.dispawn(fromRow, fromColumn, toRow, toColumn, enemyAt(fromRow, fromColumn))
                        desk.move(fromRow, fromColumn, toRow, toColumn)

                        if (!desk[toRow, toColumn]?.canEat(toRow, toColumn)?.first!!) {
                            turn = turn.enemyColor()
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns coordinates of all enemies.
     */
    private fun enemyAt(row: Int, column: Int): List<Pair<Int, Int>> {
        return desk[row, column]?.canEat(row, column)?.third!!
    }

    /**
     * Returns coordinates of all checkers that can move.
     */
    private fun canMove(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (desk[i, j] is Checker
                        && desk[i, j]!!.color == turn && desk[i, j]?.getPossibleMoves(i, j)?.isNotEmpty()!!)
                    result.add(i to j)
            }
        }
        return result
    }

    /**
     * Returns coordinates of all checkers that can eat.
     */
    private fun itCanEat(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (desk[i, j] is Checker
                        && desk[i, j]!!.color == turn && desk[i, j]?.canEat(i, j)?.first!!)
                    result.add(i to j)
            }
        }
        return result
    }

    /**
     * Combining the two previous functions into one.
     */
    fun eatAndMove(): List<Pair<Int, Int>> = if (itCanEat().isEmpty()) canMove() else itCanEat()


    /**
     * Clearing the Board and passing the turn of the move to white.
     * Need for restart.
     */
    fun clear() {
        desk.clear()
        turn = Color.WHITE
    }

    override fun hashCode(): Int = desk.hashCode() * 31 + turn.hashCode()
    override fun equals(other: Any?): Boolean = other is MotionController && desk == other.desk && turn == other.turn


}