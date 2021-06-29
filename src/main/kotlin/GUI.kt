import javafx.application.Platform
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*

fun main() = launch<GUI>()

class GUI : App(Main::class)

class Main : View(){

    override val root = linechart(
        title = "Fitness",
        y =  NumberAxis(),
        x = NumberAxis()
    ){
        createSymbols = false
        animated = false
        val series = series("Fitness")
        runAsync {
            val genetic = Genetic(
                1000,
                0.20,
                1000,
                9,
                Buses::class,
                minValue = 0,
                maxValue = 2,
                bestByLargestFitness = false
            )

            genetic.onNewGeneration{ index, generation ->
                Platform.runLater {
                    try {
                        val fitness = generation.first().fitness
                        series.data.add(XYChart.Data(index, if(fitness == Double.POSITIVE_INFINITY){
                            0.0
                        }else{
                            fitness
                        }))
                    }catch (e : Exception){}
                }
            }

            genetic.start {
                if(it.count { g -> g == 0 } > 8) return@start Double.POSITIVE_INFINITY // Debe haber menos de 8 autobuses de tipo A
                if(it.count { g -> g == 1 } > 10) return@start Double.POSITIVE_INFINITY // Debe haber menos de 10 autobuses de tipo B
                if(sitCount(it) < 400) return@start Double.POSITIVE_INFINITY // Debe haber mas de 400 lugares

                var price = 0.0

                for(gen in it){
                    price += if(gen == 0){  // Suma el precio en caso de no ser infinito
                        60
                    }else{
                        80
                    }
                }

                price
            }
        }
    }
}