package com.example.examen_con_sqlite.Model

data class Empresa(
    var id: Int = 0,
    var nombre: String,
    var ceo: String,
    var anioDeRegistro: Int,
    var sector: String,
    var productos: MutableList<Producto> = mutableListOf()
){
    // Constructor vacío
    constructor() : this(
        id = 0,
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
        id = 0,
        nombre = nombre,
        ceo = ceo,
        anioDeRegistro = anioDeRegistro,
        sector = sector
    )

}