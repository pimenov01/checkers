package controller

import addition.Checker
import addition.Color
import main.app.DeskGUI

class MotionController {
    private lateinit var desk: DeskGUI
    var turn = Color.WHITE


    fun setDeskPointer (deskGUI: DeskGUI) {
        desk = deskGUI
    }

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


    private fun enemyAt(row: Int, column: Int): List<Pair<Int, Int>> {
        return desk[row, column]?.canEat(row, column)?.third!!
    }




    private fun canMove(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (desk[i, j] is Checker //|| desk[i, j] is Queen
                        && desk[i, j]!!.color == turn && desk[i, j]?.getPossibleMoves(i, j)?.isNotEmpty()!!)
                    result.add(i to j)
            }
        }
        return result
    }

    private fun itCanEat(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (desk[i, j] is Checker //|| desk[i, j] is Queen
                        && desk[i, j]!!.color == turn && desk[i, j]?.canEat(i, j)?.first!!)
                    result.add(i to j)
            }
        }
        return result
    }


    fun eatAndMove(): List<Pair<Int, Int>> = if (itCanEat().isEmpty()) canMove() else itCanEat()

    fun clear() {
        desk.clear()
        turn = Color.WHITE

    }

    override fun hashCode(): Int = desk.hashCode() * 31 + turn.hashCode()
    override fun equals(other: Any?): Boolean = other is MotionController && desk == other.desk && turn == other.turn


}