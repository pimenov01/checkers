package main.app

import main.view.MainView
import javafx.stage.Stage
import tornadofx.App

class MyApp: App(MainView::class, Styles::class) {
    /*override fun start(stage: Stage) {
        with(stage) {
            width = 800.0
            height = 790.0
            isResizable = false
        }
        super.start(stage)
    }*/
}
