data class Spell(val name: String, val power: Int)

fun main() {
    val input = readln().split(" ")
    val spells = input.map { Spell(it.split("-")[0], it.split("-")[1].toInt()) }
    val res = spells.fold(0) {ass, i ->
        if (i.power > 40) ass + i.power else ass
    }
    println(res)
}