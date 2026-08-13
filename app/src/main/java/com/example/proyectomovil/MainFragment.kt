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

                        Pelicula(
                            id = 0,
                            title = peliculaFirebase.title,
                            image = peliculaFirebase.image,
                            director = peliculaFirebase.director,
                            anioEstreno =
                                peliculaFirebase.releaseDate
                                    .toIntOrNull() ?: 0,
                            duracionMinutos =
                                peliculaFirebase.runningTime
                                    .toIntOrNull() ?: 0,
                            calificacion =
                                (peliculaFirebase.rtScore
                                    .toIntOrNull() ?: 0) / 20,
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