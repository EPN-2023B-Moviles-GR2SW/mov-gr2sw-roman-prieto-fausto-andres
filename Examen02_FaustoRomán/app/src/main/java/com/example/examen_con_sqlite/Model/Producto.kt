package com.example.examen_con_sqlite.Model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Producto(
    @DocumentId
    var id: String = "",
    @PropertyName("empresaId")
    var empresaId: String = "",
    var nombre: String,
    var tipo: String,
    var cantidad: Int,
    var precio: Int
) {
    constructor() :this(
        id = "",
        empresaId = "",
        nombre = "",
        tipo = "",
        cantidad = 0,
        precio = 0
    )


    // Constructor para simplificar la creación de instancias
    constructor(
        empresaId: String,
        nombre: String,
        tipo: String,
        cantidad: Int,
        precio: Int
    ) : this("", empresaId, nombre, tipo, cantidad, precio)

}