

fun String.binaryStringToLong() = this.reversed().mapIndexed { i, c -> when (c) {
    '0' -> 0L
    '1' -> (1L).shl(i)
    else -> throw Exception("Formato incorrecto")
}}.sum()