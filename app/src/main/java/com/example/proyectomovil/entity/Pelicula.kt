package com.example.proyectomovil.entity

data class Pelicula(
    val id: Int,
    val title: String,
    val image: String,
    val director: String,
    val anioEstreno: Int,
    val duracionMinutos: Int,
    val calificacion: Int,
    val categoria: String
)
