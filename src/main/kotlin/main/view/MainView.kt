package main.view


import addition.Checker
import addition.Queen
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
import main.app.DeskGUI

class MainView : View("checkers") {
    override val root = BorderPane()
    private lateinit var desk: DeskGUI
    private var lastDrop: Pair<Int?, Int?> = null to null
    private var controller = MotionController()
    private var hint = SimpleBooleanProperty(true)

    init {
        primaryStage.icons.add(Image("file:src/main/resources/checkers_icon.png"))
        setWindowMinSize(800, 790)
        //primaryStage.isResizable = false

        with(root) {
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
                                            //Color.rgb(240, 217, 181)
                                         Color.web("e6ba99")
                                         else
                                            //Color.rgb(181, 136, 99)
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
                                        hintsEnabled(row, column)
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
                                        hintsDisabled(row, column)
                                        val db = event.dragboard
                                        desk.setImage(row, column, db.image)
                                        if (event.transferMode == TransferMode.MOVE) {
                                            controller.handle(row, column, lastDrop.first, lastDrop.second)
                                        }
                                        lastDrop = null to null
                                        //updateStatus()
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
            /*for (column in 1..7 step 2) {
                spawn(Checker(addition.Color.BLACK), 0, column)
                spawn(Checker(addition.Color.BLACK), 2, column)
                spawn(Checker(addition.Color.WHITE), 6, column)
            }

            for (column in 0..7 step 2) {
                spawn(Checker(addition.Color.BLACK), 1, column)
                spawn(Checker(addition.Color.WHITE), 5, column)
                spawn(Checker(addition.Color.WHITE), 7, column)
            }*/

            /**
             * Used for tests
             */
            //spawn(Checker(addition.Color.WHITE), 0, 0)
            /*spawn(Checker(addition.Color.WHITE),5, 2)
            spawn(Checker(addition.Color.BLACK),2, 4)*/
            spawn(Queen(addition.Color.WHITE), 2, 5)
            spawn(Checker(addition.Color.WHITE), 1, 4)
            spawn(Queen(addition.Color.BLACK), 4, 3)
            spawn(Checker(addition.Color.BLACK), 3, 2)

        }

    }

    /**
     * Both below needed to be fixed
     */
    private fun hintsEnabled(row: Int, column: Int) {
        val defaultColor = Color.web("a9f083")
            for ((x, y) in desk.getPossibleMoves(row, column)) {
                desk.setCellColor(x, y, defaultColor)
            }
    }

    private fun hintsDisabled(row: Int, column: Int) {
        val defaultColor = Color.web("7d3b0a")
        for ((x, y) in desk.getPossibleMoves(row, column)) {
            desk.setCellColor(x, y, defaultColor)
        }
    }
}


