package main.app

import addition.Board
import addition.Checker
import addition.Queen
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.shape.Rectangle
import javafx.scene.paint.Color


class DeskGUI(private val cells: List<List<Rectangle>>,
              private val images: List<List<ImageView>>): Board() {

    fun setImage(row: Int, column: Int, image: Image?) {
        images[row][column].image = image
    }

    fun getImage(row: Int, column: Int): Image? = images[row][column].image


    fun spawn(checker: Checker, row: Int, column: Int) {
        this[row, column] = checker
        setImage(row, column, Image("file:src/main/resources/mine/${checker}.png"))
    }

    fun dispawn2(row: Int, column: Int) {
        this[row, column] = null
        setImage(row, column, null)
    }

    fun dispawn(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int, enemyCords: List<Pair<Int, Int>>) {


        var tempRow = fromRow
        var tempColumn = fromColumn

        val vector = when {
            fromRow > toRow && fromColumn > toColumn -> -1 to -1
            fromRow > toRow && fromColumn < toColumn -> -1 to 1
            fromRow < toRow && fromColumn < toColumn -> 1 to 1
            else -> 1 to -1
        }

        while (tempRow != toRow && tempColumn != toColumn) {
            tempRow += vector.first
            tempColumn += vector.second
            if (tempRow in 0 until 8 && tempColumn in 0 until 8) {
                if (enemyCords.contains(tempRow to tempColumn)) {
                    this[tempRow, tempColumn] = null
                    setImage(tempRow, tempColumn, null)
                }
            }
        }

    }


    fun move(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int) {
        this[toRow, toColumn] = this[fromRow, fromColumn]
        this[fromRow, fromColumn] = null
        setImage(toRow, toColumn, getImage(fromRow, fromColumn))
        setImage(fromRow, fromColumn, null)

        if (this[toRow, toColumn]!!.color == addition.Color.WHITE && toRow == 0) {
            dispawn2(toRow, toColumn)
            spawn(Queen(addition.Color.WHITE), toRow, toColumn)
        } else {
           if (this[toRow, toColumn]!!.color == addition.Color.BLACK && toRow == 7) {
               dispawn2(toRow, toColumn)
               spawn(Queen(addition.Color.BLACK), toRow, toColumn)
           }
        }

    }

    fun defeated(color: addition.Color): Boolean {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                if (this[i, j] is Checker && this[i, j]!!.color == color) {
                    if (getPossibleMoves(i, j).isNotEmpty()) {
                        return false
                    }
                }
            }
        }
        return true
    }


    fun setCellColor(row: Int, column: Int, color: Color) {
        if (row in 0 until 8 && column in 0 until 8) cells[row][column].fill = color
    }

    fun clear() {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                dispawn2(i, j)
            }
        }
    }



    //override fun hashCode(): Int = info
}