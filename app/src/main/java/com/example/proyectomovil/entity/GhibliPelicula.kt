package com.example.proyectomovil.entity

import com.google.gson.annotations.SerializedName

data class GhibliPelicula(

    val id: String = "",

    val title: String = "",

    val image: String = "",

    val director: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("running_time")
    val runningTime: String = "",

    @SerializedName("rt_score")
    val rtScore: String = ""
)