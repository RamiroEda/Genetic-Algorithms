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
    private val maxValue : Int = 1
) {
    val lastPopulation = ArrayList<T>()

    init {
        assert(mutability in 0.0..1.0){
            "mutability debe estar entre 0 y 1"
        }
    }

    fun start(fitnessLambda: (IntArray) -> Double){
        generateInitialPopulation(fitnessLambda)

        for (generation in 1..generations){
            lastPopulation.sortByDescending {
                it.fitness
            }

            val selectionPopulation = ArrayList<T>()

            for(i in 0..population/2){
                val x = Random.nextDouble().pow(2)
                val randomSelectionIndex = (lastPopulation.size*x).toInt()

                selectionPopulation.add(lastPopulation[randomSelectionIndex])
                lastPopulation.removeAt(randomSelectionIndex)
            }


            val newGeneration = List<T>(population-selectionPopulation.size){
                val mother = selectionPopulation.random()
                val father = selectionPopulation.random()

                val mask = randomBooleanArray(chromosomeLength)

                val child = mother.cross(father, mask)

                if(canMutate()){
                    child.mutate {
                        val index = Random.nextInt(size)
                        set(index, if(get(index) != 0){
                            0
                        }else{
                            1
                        })
                    }
                }

                phenotypeClass.constructors.first {
                    it.parameters.size == 1
                }.call(child)
            }

            replacePopulation(selectionPopulation + newGeneration)
        }

        lastPopulation.sortByDescending {
            it.fitness
        }
    }

    private fun generateInitialPopulation(fitnessLambda: (IntArray) -> Double){
        lastPopulation.clear()
        lastPopulation.addAll(List(population){
            phenotypeClass.primaryConstructor!!.call(IntArray(chromosomeLength){
               Random.nextInt(minValue, maxValue)
            }, fitnessLambda)
        })
    }

    private fun canMutate() = mutability > Random.nextDouble()

    private fun replacePopulation(population: List<T>){
        lastPopulation.clear()
        lastPopulation.addAll(population)
    }
}