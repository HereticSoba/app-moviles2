package com.example.proyectomovil.entity

data class Favoritos_provisional (
    val id: Int,
    val idUsuario: Int,
    val title: String,
    val image: String,
    val director: String,
    val anioEstreno: Int,
    val duracionMinutos: Int,
    val calificacion: Int,
    val categoria: String,
    val fecha_agregado : String
)