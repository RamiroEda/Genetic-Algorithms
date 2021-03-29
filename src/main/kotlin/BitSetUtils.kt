import kotlin.random.Random

fun IntArray.and(another: BooleanArray) = IntArray(size){
    if (it > another.lastIndex) return@IntArray 0

    if(another[it]){
        get(it)
    }else{
        0
    }
}

fun IntArray.or(another: IntArray) = assert(size == another.size){
    "Deben tener el mismo tamaño"
}.let {
    IntArray(size){
        when{
            get(it) == 0 && another[it] != 0 -> another[it]
            get(it) != 0 && another[it] == 0 -> get(it)
            get(it) != 0 && another[it] != 0 -> get(it) * another[it]
            else -> 0
        }
    }
}

fun BooleanArray.inv() = BooleanArray(size){
    !get(it)
}

fun randomIntArray(size: Int) = IntArray(size){
    Random.nextInt(2)
}

fun randomBooleanArray(size: Int) = BooleanArray(size){
    Random.nextBoolean()
}