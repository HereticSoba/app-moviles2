package com.example.proyectomovil.api

import com.example.proyectomovil.entity.GhibliPelicula
import retrofit2.Call
import retrofit2.http.GET

interface GhibliApiService {

    @GET("films")
    fun getPeliculas(): Call<List<GhibliPelicula>>
}