package com.example.examen_con_sqlite.Model

data class Producto(
    var id: Int,
    var empresaId: Int,
    var nombre: String,
    var tipo: String,
    var cantidad: Int,
    var precio: Int
) {

    // Constructor para simplificar la creación de instancias
    constructor(
        empresaId: Int,
        nombre: String,
        tipo: String,
        cantidad: Int,
        precio: Int
    ) : this(0, empresaId, nombre, tipo, cantidad, precio)

}