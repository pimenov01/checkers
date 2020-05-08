package main.app

import addition.Board
import addition.Checker
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

    fun move(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int) {
        val deleted = this[toRow, toColumn]
        this[toRow, toColumn] = this[fromRow, fromColumn]
        this[fromRow, fromColumn] = null
        setImage(toRow, toColumn, getImage(fromRow, fromColumn))
        setImage(fromRow, fromColumn, null)

    }

    fun setCellColor(row: Int, column: Int, color: Color) {
        cells[row][column].fill = color
    }


    //override fun hashCode(): Int = info
}