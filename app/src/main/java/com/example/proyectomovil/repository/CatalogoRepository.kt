package com.example.proyectomovil.repository

import android.util.Log
import com.example.proyectomovil.api.GhibliApiClient
import com.example.proyectomovil.entity.GhibliPelicula
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CatalogoRepository {

    private val database =
        FirebaseDatabase.getInstance().getReference("peliculas")


    // API -> FIREBASE
    fun cargarPeliculasAFirebase() {

        GhibliApiClient.apiService.getPeliculas()
            .enqueue(object : Callback<List<GhibliPelicula>> {

                override fun onResponse(
                    call: Call<List<GhibliPelicula>>,
                    response: Response<List<GhibliPelicula>>
                ) {

                    if (response.isSuccessful) {

                        val peliculas = response.body() ?: return

                        val datos = mutableMapOf<String, Any>()

                        peliculas.take(15).forEach { pelicula ->
                            datos[pelicula.id] = pelicula
                        }

                        database.setValue(datos)
                            .addOnSuccessListener {

                                Log.d(
                                    "FIREBASE",
                                    "Películas guardadas: ${datos.size}"
                                )
                            }
                            .addOnFailureListener { error ->

                                Log.e(
                                    "FIREBASE",
                                    "Error guardando: ${error.message}"
                                )
                            }

                    } else {

                        Log.e(
                            "FIREBASE",
                            "Error API: ${response.code()}"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<List<GhibliPelicula>>,
                    t: Throwable
                ) {

                    Log.e(
                        "FIREBASE",
                        "Error Retrofit: ${t.message}"
                    )
                }
            })
    }


    // FIREBASE -> ANDROID
    fun obtenerPeliculasFirebase(
        onResultado: (List<GhibliPelicula>) -> Unit
    ) {

        database.get()
            .addOnSuccessListener { snapshot ->

                val lista = mutableListOf<GhibliPelicula>()

                for (hijo in snapshot.children) {

                    val pelicula =
                        hijo.getValue(GhibliPelicula::class.java)

                    if (pelicula != null) {
                        lista.add(pelicula)
                    }
                }

                onResultado(lista)
            }
            .addOnFailureListener { error ->

                Log.e(
                    "FIREBASE",
                    "Error leyendo películas: ${error.message}"
                )

                onResultado(emptyList())
            }
    }
}