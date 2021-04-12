import javafx.application.Platform
import javafx.beans.Observable
import javafx.collections.ObservableList
import javafx.scene.Parent
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*

fun main() = launch<GUI>()

class GUI : App(Main::class)

class Main : View(){
    override val root = vbox {
        linechart(
            title = "Fitness",
            y =  NumberAxis(),
            x = NumberAxis()
        ){
            createSymbols = false
            animated = false
            val series = series("Fitness")
            runAsync {
                nQueensProblem { index, generation ->
                    Platform.runLater {
                        series.data.add(XYChart.Data(index, generation.first().fitness))
                    }
                }
            }
        }
    }
}