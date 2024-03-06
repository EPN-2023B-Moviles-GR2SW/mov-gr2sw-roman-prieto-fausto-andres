package com.example.examen_con_sqlite.Model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Empresa(
    @DocumentId                         // Marca el campo como el ID del documento en Firestore
    @get:PropertyName("id")             // Indica que el getter de la propiedad "id" usa el nombre "id" en Firestore
    @set:PropertyName("id")
    var id: String = "",
    var nombre: String,
    var ceo: String,
    var anioDeRegistro: Int,
    var sector: String,
    var productos: MutableList<Producto> = mutableListOf()
){
    // Constructor vacío
    constructor() : this(
        id = "",
        nombre = "",
        ceo = "",
        anioDeRegistro = 0,
        sector = ""
    )


    // Constructor para crear un álbum sin proporcionar un id
    constructor(
        nombre: String,
        ceo: String,
        anioDeRegistro: Int,
        sector: String
    ) : this(
        id = "",
        nombre = nombre,
        ceo = ceo,
        anioDeRegistro = anioDeRegistro,
        sector = sector
    )
}