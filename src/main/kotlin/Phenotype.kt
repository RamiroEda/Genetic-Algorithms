class Phenotype(
    val value: Long
) {
    val fitness : Long
        get() = value * value

    fun cross(other: Phenotype, mask: String) = Companion.cross(this, other, mask)

    companion object {
        fun cross(
            phenotype1: Phenotype,
            phenotype2: Phenotype,
            mask: String
        ) : Pair<Phenotype, Phenotype> {
            val maskLong = mask.binaryStringToLong()

            val activatedP1 = phenotype1.value.and(maskLong)
            val activatedP2 = phenotype2.value.and(maskLong)

            val deactivatedP1 = phenotype1.value.and(maskLong.inv())
            val deactivatedP2 = phenotype2.value.and(maskLong.inv())

            val v1 = activatedP1.or(deactivatedP2)
            val v2 = activatedP2.or(deactivatedP1)

            return Pair(Phenotype(v1), Phenotype(v2))
        }
    }

    override fun toString() = """
        ---------------------------------------------
        genotype = ${value.toString(2)}
        
        fitness = $fitness
        ---------------------------------------------
    """.trimIndent()
}