import com.example.examenb1.Producto

data class Empresa(
    var nombre: String="",
    var ceo: String="",
    var anioDeRegistro: Int=2024,
    var sector: String="",
    var productos: MutableList<Producto> = arrayListOf(),

    ){

}
