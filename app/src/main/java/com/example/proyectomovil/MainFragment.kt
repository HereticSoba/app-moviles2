package com.example.proyectomovil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.adapters.PeliculaAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.proyectomovil.repository.CatalogoRepository
import com.example.proyectomovil.entity.Pelicula
import com.example.proyectomovil.Data.AppDatabaseHelper

class MainFragment : Fragment() {

    private lateinit var rvPeliculas: RecyclerView
    private lateinit var peliculaAdapter: PeliculaAdapter
    private lateinit var etBuscar: TextInputEditText
    private lateinit var btnBuscar: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.fragment_main,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        etBuscar = view.findViewById(R.id.etBuscar)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        rvPeliculas = view.findViewById(R.id.rvPeliculas)

        rvPeliculas.layoutManager =
            LinearLayoutManager(requireContext())

        CatalogoRepository.cargarPeliculasAFirebase()

        cargarPeliculas("")

        btnBuscar.setOnClickListener {

            val texto =
                etBuscar.text.toString().trim()

            cargarPeliculas(texto)
        }

        etBuscar.setOnEditorActionListener { _, actionId, _ ->

            if (
                actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {

                val texto =
                    etBuscar.text.toString().trim()

                cargarPeliculas(texto)

                true

            } else {

                false
            }
        }
    }

    private fun cargarPeliculas(query: String) {

        val dbHelper = AppDatabaseHelper(requireContext())

        CatalogoRepository.obtenerPeliculasFirebase { peliculasFirebase ->

            val peliculas =
                peliculasFirebase
                    .filter { pelicula ->

                        pelicula.title.contains(
                            query,
                            ignoreCase = true
                        )
                    }
                    .map { peliculaFirebase ->

                        val anio = peliculaFirebase.releaseDate.toIntOrNull() ?: 0
                        val duracion = peliculaFirebase.runningTime.toIntOrNull() ?: 0
                        val calificacion = (peliculaFirebase.rtScore.toIntOrNull() ?: 0) / 20

                        // clave del arreglo: se busca/inserta en la tabla pelicula
                        // y se obtiene un id REAL (nunca 0)
                        val idReal = dbHelper.obtenerOInsertarPelicula(
                            titulo = peliculaFirebase.title,
                            imagen = peliculaFirebase.image,
                            director = peliculaFirebase.director,
                            categoria = "Animación",
                            anioEstreno = anio,
                            duracionMinutos = duracion,
                            calificacion = calificacion,
                            firebaseId = peliculaFirebase.id
                        )

                        Pelicula(
                            id = idReal,
                            title = peliculaFirebase.title,
                            image = peliculaFirebase.image,
                            director = peliculaFirebase.director,
                            anioEstreno = anio,
                            duracionMinutos = duracion,
                            calificacion = calificacion,
                            categoria = "Animación",
                            firebaseId = peliculaFirebase.id
                        )
                    }

            peliculaAdapter =
                PeliculaAdapter(
                    requireContext(),
                    peliculas
                ) { pelicula ->

                    val intent =
                        Intent(
                            requireContext(),
                            PeliculaDetalleActivity::class.java
                        )

                    intent.putExtra(
                        "image",
                        pelicula.image
                    )

                    intent.putExtra(
                        "id",
                        pelicula.id
                    )

                    intent.putExtra(
                        "firebaseId",
                        pelicula.firebaseId
                    )

                    intent.putExtra(
                        "titulo",
                        pelicula.title
                    )

                    intent.putExtra(
                        "director",
                        pelicula.director
                    )

                    intent.putExtra(
                        "anio",
                        pelicula.anioEstreno
                    )

                    intent.putExtra(
                        "duracion",
                        pelicula.duracionMinutos
                    )

                    intent.putExtra(
                        "calificacion",
                        pelicula.calificacion
                    )

                    intent.putExtra(
                        "categoria",
                        pelicula.categoria
                    )

                    startActivity(intent)
                }

            rvPeliculas.adapter =
                peliculaAdapter
        }
    }
}