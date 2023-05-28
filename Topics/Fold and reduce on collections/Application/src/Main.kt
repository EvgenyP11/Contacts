data class Ship(val name: String, val ammunition: Int)

fun main() {
    val ships = readln().split(" ")
    val shipsList = ships.map { Ship(it.split("-")[0], it.split("-")[1].toInt()) }
    val res = shipsList.fold(0) {sum, it ->
        if (it.name.first().uppercase() == "T"
            && it.ammunition > 20) sum + it.ammunition else sum
    }
    println(res)
}