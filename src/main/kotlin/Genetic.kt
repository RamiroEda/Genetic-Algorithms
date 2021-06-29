import kotlin.math.pow
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class Genetic<T : Phenotype>(
    private val generations : Int,
    private val mutability : Double,
    private val population: Int,
    private val chromosomeLength : Int,
    private val phenotypeClass : KClass<T>,
    private val minValue : Int = 0,
    private val maxValue : Int = 1,
    private val bestByLargestFitness : Boolean = true
) {
    val lastPopulation = ArrayList<T>()
    private var onNewGenerationCallback : (Int, List<Phenotype>) -> Unit = {_,_->}
    var mutationFunction : IntArray.() -> Unit = {
        val index = Random.nextInt(size)
        set(index, Random.nextInt(minValue, maxValue))
    }
    var onFinish : (List<Phenotype>) -> Unit = {}

    init {
        assert(mutability in 0.0..1.0){
            "mutability debe estar entre 0 y 1"
        }
    }

    fun start(fitnessLambda: (IntArray) -> Double){
        generateInitialPopulation(fitnessLambda)

        for (generation in 1..generations){
            if(bestByLargestFitness){
                lastPopulation.sortByDescending {
                    it.fitness
                }
            }else{
                lastPopulation.sortBy {
                    it.fitness
                }
            }

            if(generation % 1 == 0 || generation == generations){
                println(lastPopulation.first())
                onNewGenerationCallback(generation, lastPopulation.toList())
            }

            val selectionPopulation = ArrayList<T>()

            for(i in 0..population/2){
                val x = Random.nextDouble().pow(2)
                val randomSelectionIndex = (lastPopulation.size*x).toInt()

                selectionPopulation.add(lastPopulation[randomSelectionIndex])
                lastPopulation.removeAt(randomSelectionIndex)
            }


            val newGeneration = List(population-selectionPopulation.size){
                val mother = selectionPopulation.random()
                val father = selectionPopulation.random()

                val mask = randomBooleanArray(chromosomeLength)

                val child = mother.cross(father, mask)

                if(canMutate()){
                    child.mutate(mutationFunction)
                }

                phenotypeClass.constructors.first {
                    it.parameters.size == 1
                }.call(child)
            }

            replacePopulation(selectionPopulation + newGeneration)
        }

        if(bestByLargestFitness){
            lastPopulation.sortByDescending {
                it.fitness
            }
        }else{
            lastPopulation.sortBy {
                it.fitness
            }
        }

        onFinish(lastPopulation)
    }

    private fun generateInitialPopulation(fitnessLambda: (IntArray) -> Double){
        lastPopulation.clear()
        lastPopulation.addAll(List(population){
            phenotypeClass.primaryConstructor!!.call(IntArray(chromosomeLength){
               Random.nextInt(minValue, maxValue)
            }, fitnessLambda)
        })
    }

    fun onNewGeneration(callback : (Int, List<Phenotype>) -> Unit){
        this.onNewGenerationCallback = callback
    }

    private fun canMutate() = mutability > Random.nextDouble()

    private fun replacePopulation(population: List<T>){
        lastPopulation.clear()
        lastPopulation.addAll(population)
    }
}