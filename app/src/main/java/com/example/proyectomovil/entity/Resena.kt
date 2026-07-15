package com.example.proyectomovil.entity

data class Resena(
    val id: Int = 0,
    val idPelicula: Int,
    val comentario: String,
    val calificacion: Float
)
