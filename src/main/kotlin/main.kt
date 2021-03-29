fun main() {
    nQueensProblem()
//    print(countAttacks(listOf(
//        Pair(7,1),
//        Pair(6,2),
//    )))
}


fun nQueensProblem(){
    val tablero = Pair(8,8)
    val area = tablero.first * tablero.second

    val genetic = Genetic(1000, 0.20, 1000, tablero.second, Table::class, maxValue = tablero.first)

    genetic.start {
        val queens = List(it.size){ i -> Pair(it[i], i) }

        val attacks = countAttacks(queens)

        if(attacks == 0){
            2.0 * area * queens.size
        }else{
            area/attacks.toDouble()
        }
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