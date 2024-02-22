package com.example.recyclerview

import java.io.Serializable

data class User (
    var idImagen:Int,
    var nombre: String,
    var genero: String,
    var tag: String,
    var edad: Int
) : Serializable