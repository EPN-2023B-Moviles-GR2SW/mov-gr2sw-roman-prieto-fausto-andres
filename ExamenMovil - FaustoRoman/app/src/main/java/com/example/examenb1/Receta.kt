data class Producto(
    var nombre: String="",
    var nacionalidad: String="",
    var tiempoPreparacion:Int=0,
    var ingredientes: MutableList<String> = mutableListOf(),
    var pasos: MutableList<String> = mutableListOf(),
)
