fun nQueensProblem(onNewGenerationCallback : (Int, List<Phenotype>) -> Unit){
    val tablero = Pair(125, 125)
    val area = tablero.first * tablero.second

    val genetic = Genetic(
        10000,
        0.30,
        100,
        tablero.second,
        Table::class,
        minValue = 0,
        maxValue = tablero.first,
        bestByLargestFitness = false
    )

    genetic.onNewGeneration(onNewGenerationCallback)

    genetic.start {
        val queens = List(it.size){ i -> Pair(it[i], i) }

        countAttacks(queens).toDouble()
    }

    genetic.lastPopulation.forEach {
        println(it)
    }
}

fun countAttacks(queens : List<Pair<Int, Int>>) : Int{
    var count = 0

    queens.forEach { q1 ->
        queens.forEach { q2 ->
            if(q1 !== q2){
                // Horizontal
                if(q1.first == q2.first) count++

                // Vertical
                if(q1.second == q2.second) count++

                // Diagonales
                val currentX = q1.second
                val currentY = q1.first

                val realX = q2.second
                val realY = q2.first

                val expectedYUp = realX + currentY - currentX
                val expectedYDown = -realX + currentY + currentX

                if(expectedYUp == realY) count++
                if(expectedYDown == realY) count++
            }
        }
    }

    return count
}