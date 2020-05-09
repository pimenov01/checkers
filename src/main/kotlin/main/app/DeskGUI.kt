package main.app

import addition.Board
import addition.Checker
import addition.Queen
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.shape.Rectangle
import javafx.scene.paint.Color
import javafx.scene.shape.Circle


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

    fun dispawn(row: Int, column: Int) {
        this[row, column] = null
        setImage(row, column, null)
    }


    fun move(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int) {
        //val deleted = this[toRow, toColumn]
        this[toRow, toColumn] = this[fromRow, fromColumn]
        this[fromRow, fromColumn] = null
        setImage(toRow, toColumn, getImage(fromRow, fromColumn))
        setImage(fromRow, fromColumn, null)

        if (this[toRow, toColumn]!!.color == addition.Color.WHITE && toRow == 0) {
            dispawn(toRow, toColumn)
            spawn(Queen(addition.Color.WHITE), toRow, toColumn)
        } else {
           if (this[toRow, toColumn]!!.color == addition.Color.BLACK && toRow == 7) {
               dispawn(toRow, toColumn)
               spawn(Queen(addition.Color.BLACK), toRow, toColumn)
           }
        }

    }

    fun setCellColor(row: Int, column: Int, color: Color) {
        cells[row][column].fill = color
    }


    //override fun hashCode(): Int = info
}