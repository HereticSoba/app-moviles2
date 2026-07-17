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
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainFragment : Fragment() {

    private lateinit var rvPeliculas: RecyclerView
    private lateinit var peliculaAdapter: PeliculaAdapter
    private lateinit var etBuscar: TextInputEditText
    private lateinit var btnBuscar: MaterialButton
    private lateinit var helper: AppDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helper = AppDatabaseHelper(requireContext())
        etBuscar = view.findViewById(R.id.etBuscar)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        rvPeliculas = view.findViewById(R.id.rvPeliculas)
        rvPeliculas.layoutManager = LinearLayoutManager(requireContext())

        cargarPeliculas("")

        btnBuscar.setOnClickListener {
            val texto = etBuscar.text.toString().trim()
            cargarPeliculas(texto)
        }

        etBuscar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val texto = etBuscar.text.toString().trim()
                cargarPeliculas(texto)
                true
            } else {
                false
            }
        }
    }

    private fun cargarPeliculas(query: String) {
        val peliculas = helper.obtenerPeliculas(query)
        peliculaAdapter = PeliculaAdapter(requireContext(), peliculas) { pelicula ->
            val intent = Intent(requireContext(), PeliculaDetalleActivity::class.java)
            intent.putExtra("image", pelicula.image)
            intent.putExtra("titulo", pelicula.title)
            intent.putExtra("director", pelicula.director)
            intent.putExtra("anio", pelicula.anioEstreno)
            intent.putExtra("duracion", pelicula.duracionMinutos)
            intent.putExtra("calificacion", pelicula.calificacion)
            intent.putExtra("categoria", pelicula.categoria)
            startActivity(intent)
        }
        rvPeliculas.adapter = peliculaAdapter
    }
}