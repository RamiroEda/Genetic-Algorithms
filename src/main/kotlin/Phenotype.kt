import kotlin.random.Random

open class Phenotype(
    chromosome : IntArray,
    val fitnessLambda: (IntArray) -> Double
) {
    constructor(phenotype: Phenotype) : this(
        phenotype.chromosome,
        phenotype.fitnessLambda
    )

    var chromosome = chromosome
        private set

    val fitness : Double
        get() = fitnessLambda(chromosome)

    fun cross(other: Phenotype, mask: BooleanArray) = Companion.cross(this, other, mask)

    fun clone() = Phenotype(this)

    companion object {
        fun cross(
            phenotype1: Phenotype,
            phenotype2: Phenotype,
            mask: BooleanArray
        ) : Phenotype {
            val activatedP1 = phenotype1.chromosome.and(mask)
            val activatedP2 = phenotype2.chromosome.and(mask)

            val deactivatedP1 = phenotype1.chromosome.and(mask.inv())
            val deactivatedP2 = phenotype2.chromosome.and(mask.inv())

            val v1 = activatedP1.or(deactivatedP2)
            val v2 = activatedP2.or(deactivatedP1)

            val child1 = Phenotype(v1, phenotype1.fitnessLambda)
            val child2 = Phenotype(v2, phenotype2.fitnessLambda)

            return if(child1.fitness > child2.fitness){
                child1
            }else{
                child2
            }
        }
    }

    fun mutate(mutationLambda : IntArray.() -> Unit){
        mutationLambda(chromosome)
    }

    override fun toString() = """
        ---------------------------------------------
        phenotype       = ${chromosome.sum()}
        genotype        = ${chromosome.joinToString(" ")}
        
        fitness         = $fitness
        ---------------------------------------------
    """.trimIndent()
}