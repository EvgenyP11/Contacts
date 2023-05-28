fun main() {
    val list = readln().split(" ").map { it.toDouble() }
    val res = list.fold(0.0) { ass, i -> ass + i }
    println("${res / list.size}")
}