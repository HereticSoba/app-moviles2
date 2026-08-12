package com.example.proyectomovil.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GhibliApiClient {

    private val BASE_URL = "https://ghibliapi.vercel.app/"

    val apiService : GhibliApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GhibliApiService::class.java)
    }

}