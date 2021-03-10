import java.util.*

fun main(args: Array<String>) {
    val p1 = Phenotype("10011".binaryStringToLong())
    val p2 = Phenotype("01111".binaryStringToLong())
    val mask = "11000"

    val res = Phenotype.cross(p1, p2, mask)

    print(res)
}