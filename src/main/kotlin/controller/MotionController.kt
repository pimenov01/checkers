package controller

import addition.Board
import addition.Checker
import addition.Color
import main.app.DeskGUI

class MotionController {
    private lateinit var desk: DeskGUI
    private var turn = Color.WHITE
    private val checker: Checker = Checker(turn)

    fun nowTurn () = turn

    fun setDeskPointer (deskGUI: DeskGUI) {
        desk = deskGUI
    }

    fun handle (fromRow: Int, fromColumn: Int, toRow: Int?, toColumn: Int?) {
       /* var tempRow = fromRow
        var tempColumn = fromColumn
        if (toRow == null || toColumn == null) {
            return
        }
        println("they can move: ${canMove()}")
        println("they can eat: ${itCanEat()}")
        println("they can eat so they should move: ${eatAndMove()}")

        if (desk[fromRow, fromColumn] is Checker && desk[fromRow, fromColumn]!!.color == turn) {
            //println("${desk.getPossibleMoves(fromRow, fromColumn)}")

            //println("checker.canEat() ${checker.canEat(tempRow, tempColumn).first}")

            *//*if (desk[tempRow, tempColumn]?.canEat(tempRow, tempColumn)?.first!!) {
                while (desk[tempRow, tempColumn]?.canEat(tempRow, tempColumn)?.first!!) {
                    if (toRow to toColumn in desk.getPossibleMoves(tempRow, tempColumn)) {
                        desk.move(tempRow, tempColumn, toRow, toColumn)
                    }
                    tempColumn = toColumn
                    tempRow = toRow
                }
            } else {*//*

            if (toRow to toColumn in desk.getPossibleMoves(fromRow, fromColumn)) {
                *//*if (eatAndMove().isEmpty()) {
                desk.move(fromRow, fromColumn, toRow, toColumn)
                } else {
                    if (toRow to toColumn in eatAndMove()) {
                        desk.move(fromRow, fromColumn, toRow, toColumn)
                    }
                }*//*
                desk.move(fromRow, fromColumn, toRow, toColumn)
            }

                //println("$fromRow, $fromColumn, $toRow, $toColumn")
                //DO CHECK 4 EAT
                //} else }
                turn = turn.enemyColor()
        }*/

        if (toRow == null || toColumn == null) {
            return
        }

        if (desk[fromRow, fromColumn] is Checker && desk[fromRow, fromColumn]!!.color == turn) {
            if (fromRow to fromColumn in eatAndMove()) {
                desk.move(fromRow, fromColumn, toRow, toColumn)
                while (desk[toRow, toColumn]?.canEat(toRow, toColumn)?.first!!) {
                    for ((tempRow, tempColumn) in desk[toRow, toColumn]?.canEat(toRow, toColumn)?.second!!) {
                        desk.move(toRow, toColumn, tempRow, tempColumn)
                    }
                }
                turn = turn.enemyColor()
            }
        }

        /*if (desk[fromRow, fromColumn] is Checker && desk[fromRow, fromColumn]!!.color == turn) {
            println("1")
            if (toRow to toColumn in desk.getPossibleMoves(fromRow, fromColumn)) {
                println("2")
                if (eatAndMove().isEmpty()) {
                    println("3")
                    desk.move(fromRow, fromColumn, toRow, toColumn)
                    println("4")
                } else {
                    if (toRow to toColumn in eatAndMove())
                        println("5")
                        desk.move(fromRow, fromColumn, toRow, toColumn)
                    println("6")
                }
                turn = turn.enemyColor()
                println("7")
            }
        }*/



    }



    /*private fun isEating(): Boolean {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (desk[i, j] == checker && desk[i, j]!!.canEat(i, j).first) {
                    return true
                }
            }
        }
        return false
    }*/

    private fun canMove(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (desk[i, j] is Checker && desk[i, j]!!.color == turn && desk[i, j]?.getPossibleMoves(i, j)?.isNotEmpty()!!)
                    result.add(i to j)
            }
        }
        return result
    }

    private fun itCanEat(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (desk[i, j] is Checker && desk[i, j]!!.color == turn && desk[i, j]?.canEat(i, j)?.first!!)
                    result.add(i to j)
            }
        }
        return result
    }

    /*private fun move(fromRow: Int, fromColumn: Int, toRow: Int?, toColumn: Int?): Pair<Int?, Int?> {
        if (toRow == null || toColumn == null) {
            return null to null
        }

        if (desk[fromRow, fromColumn] is Checker && desk[fromRow, fromColumn]!!.color == turn) {
            if (toRow to toColumn in desk.getPossibleMoves(fromRow, fromColumn)) {
                desk.move(fromRow, fromColumn, toRow, toColumn)
            }
        }
        return toRow to toColumn

    }*/

    private fun eatAndMove(): List<Pair<Int, Int>> = if (itCanEat().isEmpty()) canMove() else itCanEat()






    fun clear() {
        desk.clear()
        turn = Color.WHITE
    }

    override fun hashCode(): Int = desk.hashCode() * 31 + turn.hashCode()
    override fun equals(other: Any?): Boolean = other is MotionController && desk == other.desk && turn == other.turn


}