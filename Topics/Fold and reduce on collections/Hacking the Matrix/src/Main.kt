fun main() {
    val list = readln().split(" ")
    var res: Int = 0
        list.forEach { if (it.length >= 4) res += it.length}


    println(res)
}