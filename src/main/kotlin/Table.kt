class Table(
    chromosome : IntArray,
    fitnessLambda: (IntArray) -> Double
) : Phenotype(chromosome, fitnessLambda) {
    constructor(phenotype: Phenotype) : this(phenotype.chromosome, phenotype.fitnessLambda)

    override fun toString(): String {
        val queens = List(chromosome.size){ i -> Pair(chromosome[i], i) }
        val stringBuilder = StringBuilder()

        stringBuilder.appendLine("----------- Tablero $fitness ------------")
        for (y in chromosome.indices){
            for (x in chromosome.indices){
                val queen = if(queens.firstOrNull{it.first == y && it.second == x} == null){
                    if((y+x)%2 == 0){
                        "░"
                    }else{
                        "█"
                    }
                }else{
                    "X"
                }
                stringBuilder.append(if((y+x)%2 == 0){
                    "░$queen░"
                }else{
                    "█$queen█"
                })
            }
            stringBuilder.appendLine()
        }
        stringBuilder.appendLine("----------------------------------------------")

        return stringBuilder.toString()
    }
}