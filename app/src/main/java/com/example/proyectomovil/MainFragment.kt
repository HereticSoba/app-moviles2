package com.example.proyectomovil

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.adapters.PeliculaAdapter
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.google.android.material.textfield.TextInputEditText


class MainFragment : Fragment() {

    private lateinit var rvPeliculas: RecyclerView
    private lateinit var peliculaAdapter: PeliculaAdapter
    private lateinit var etBuscar: TextInputEditText
    private lateinit var helper: AppDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        helper = AppDatabaseHelper(requireContext())
        etBuscar = view.findViewById(R.id.etBuscar)
        rvPeliculas = view.findViewById(R.id.rvPeliculas)
        rvPeliculas.layoutManager = LinearLayoutManager(requireContext())

        cargarPeliculas("")

        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                cargarPeliculas(s.toString())
            }
        })
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