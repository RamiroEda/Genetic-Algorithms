import kotlin.random.Random

class Phenotype(
    val chromosome : Long
) {
    val fitness = chromosome.times(chromosome)

    fun cross(other: Phenotype, mask: String) = Companion.cross(this, other, mask)

    fun clone() = fromPhenotype(this)

    companion object {
        fun cross(
            phenotype1: Phenotype,
            phenotype2: Phenotype,
            mask: String
        ) : Pair<Phenotype, Phenotype> {
            val maskLong = mask.binaryStringToLong()

            val activatedP1 = phenotype1.chromosome.and(maskLong)
            val activatedP2 = phenotype2.chromosome.and(maskLong)

            val deactivatedP1 = phenotype1.chromosome.and(maskLong.inv())
            val deactivatedP2 = phenotype2.chromosome.and(maskLong.inv())

            val v1 = activatedP1.or(deactivatedP2)
            val v2 = activatedP2.or(deactivatedP1)

            return Pair(Phenotype(v1), Phenotype(v2))
        }

        fun fromPhenotype(
            phenotype: Phenotype
        ) = Phenotype(phenotype.chromosome)

        fun random() = Phenotype(Random.nextLong())
    }

    override fun toString() = """
        ---------------------------------------------
        phenotype       = $chromosome
        genotype        = ${chromosome.toString(2)}
        
        fitness         = $fitness
        ---------------------------------------------
    """.trimIndent()
}