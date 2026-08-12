package com.example.proyectomovil.api

import com.example.proyectomovil.entity.Pelicula
import retrofit2.Call
import retrofit2.http.GET

interface GhibliApiService {

    @GET("peliculas")
    fun getpeliculas() : Call<List<Pelicula>>


}