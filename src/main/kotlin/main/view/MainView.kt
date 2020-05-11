package main.view


import addition.Checker
import controller.MotionController
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.Dragboard
import javafx.scene.input.TransferMode
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import tornadofx.*
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import main.app.DeskGUI

class MainView : View("checkers") {
    override val root = BorderPane()
    private lateinit var desk: DeskGUI
    private var lastDrop: Pair<Int?, Int?> = null to null
    private var controller = MotionController()
    private var hint = SimpleBooleanProperty(false)
    private var turn = text("")

    init {
        primaryStage.icons.add(Image("file:src/main/resources/checkers_icon.png"))
        setWindowMinSize(800, 840)
        //primaryStage.isResizable = false

        with(root) {
            top {
                vbox {
                    menubar {
                        menu("Hints") {
                            item("Enable hints") {
                                checkbox("", hint)
                            }
                        }
                        menu("Restart") {
                            item("Restart Game").action {restartGame()} //restartGame() }
                        }
                    }
                    borderpane {
                        center {
                            turn = text("White to move") {
                                fill = Color.BLACK
                                font = Font(20.0)
                            }
                        }
                    }
                }

            }
            center {
                gridpane {
                    val setUpSells = List(8) { MutableList(8) { Rectangle() } }
                    val setUpImage = List(8) { MutableList(8) { ImageView() } }
                    for (row in 0 until 8) {
                        row {
                            for (column in 0 until 8) {
                                stackpane {
                                    setUpSells[row][column] = rectangle {
                                        fill = if ((row + column) % 2 == 0)
                                         Color.web("e6ba99")
                                         else
                                        Color.web("7d3b0a")

                                        widthProperty().bind(root.widthProperty().divide(8))
                                        heightProperty().bind(widthProperty() - 4)
                                    }

                                    setUpImage[row][column] = imageview {
                                        fitHeightProperty().bind(
                                                setUpSells[row][column].heightProperty() / 11 * 10)
                                        fitWidthProperty().bind(fitHeightProperty())
                                    }

                                    onDragDetected = EventHandler { event ->
                                        if (hint.value && desk[row, column] is Checker && row to column in controller.eatAndMove()) {
                                            allowHints(row, column, true)
                                        }
                                        val db: Dragboard = startDragAndDrop(TransferMode.MOVE)
                                        val content = ClipboardContent()
                                        content.putImage(desk.getImage(row, column))
                                        db.setContent(content)
                                        desk.setImage(row, column, null)
                                        event.consume()
                                    }

                                    onDragOver = EventHandler { event ->
                                        if (event.gestureSource != this && event.dragboard.hasImage()) {
                                            event.acceptTransferModes(*TransferMode.ANY)
                                        }
                                        event.consume()
                                    }

                                    onDragDropped = EventHandler { event ->
                                        val db = event.dragboard
                                        var succes = false
                                        if (db.hasImage()) {
                                            succes = true
                                            lastDrop = row to column
                                        }
                                        event.isDropCompleted = succes
                                        event.consume()
                                    }

                                    onDragDone = EventHandler { event ->
                                        allowHints(row, column, false)
                                        val db = event.dragboard
                                        desk.setImage(row, column, db.image)
                                        if (event.transferMode == TransferMode.MOVE) {
                                            controller.handle(row, column, lastDrop.first, lastDrop.second)
                                        }
                                        lastDrop = null to null
                                        moveQueue()
                                        event.consume()
                                    }
                                }
                            }

                        }
                    }
                    desk = DeskGUI(setUpSells, setUpImage)
                    controller.setDeskPointer(desk)
                }
            }
        }
        spawner()
    }


    private fun spawner() {
        with(desk) {
            for (column in 1..7 step 2) {
                spawn(Checker(addition.Color.BLACK), 0, column)
                spawn(Checker(addition.Color.BLACK), 2, column)
                spawn(Checker(addition.Color.WHITE), 6, column)
            }

            for (column in 0..7 step 2) {
                spawn(Checker(addition.Color.BLACK), 1, column)
                spawn(Checker(addition.Color.WHITE), 5, column)
                spawn(Checker(addition.Color.WHITE), 7, column)
            }
        }
    }

    private fun moveQueue() {
        val tempColor = controller.turn

        turn.apply {
            text = "$tempColor`s turn"
        }

        if (desk.defeated(tempColor)) {
            turn.apply {
                text = "$tempColor lost :c You can restart game in menu."
            }
        }
    }

    private fun restartGame() {
        controller.clear()
        spawner()
        moveQueue()
    }

    private fun allowHints(row: Int, column: Int, permission: Boolean) {
       val color =  if (permission) Color.web("a9f083") else Color.web("7d3b0a")
        for ((x, y) in desk.getPossibleMoves(row, column)) {
            desk.setCellColor(x, y, color)
        }

    }
}


