
fun sitCount(chromosome: IntArray) : Int{
    var count = 0

    for (gen in chromosome){
        count += if(gen == 0){
            40
        }else{
            50
        }
    }

    return count
}


class Buses(
    chromosome : IntArray,
    fitnessLambda: (IntArray) -> Double
) : Phenotype(chromosome, fitnessLambda){
    constructor(phenotype: Phenotype) : this(
        phenotype.chromosome,
        phenotype.fitnessLambda
    )

    override fun toString(): String {
        return """
------------- Autobuses ---------------
${chromosome.joinToString("\n") { if (it == 0) "Tipo A\t\t\$60" else "Tipo B\t\t\$80" }}
Total       $${chromosome.sumBy { if(it == 0) 60 else 80}}
Pasajeros   ${sitCount(chromosome)}
        """.trimIndent()
    }
}