package com.example.proyectomovil.ui.Historial

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.example.proyectomovil.R
import com.example.proyectomovil.adapters.PeliculaAdapter

class HistorialFragment : Fragment() {

    private lateinit var rvHistorial: RecyclerView
    private lateinit var historialAdapter: PeliculaAdapter
    private lateinit var helper: AppDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helper = AppDatabaseHelper(requireContext())

        rvHistorial = view.findViewById(R.id.rvHistorial)
        rvHistorial.layoutManager = LinearLayoutManager(requireContext())

        val preferencias = requireContext().getSharedPreferences(
            "sesion",
            Context.MODE_PRIVATE
        )

        val idUsuario = preferencias.getInt("id_usuario", -1)

        val peliculasVistas = helper.obtenerHistorial(idUsuario)

        historialAdapter = PeliculaAdapter(requireContext(), peliculasVistas) { pelicula ->
            // Acción al pulsar una película del historial
        }

        rvHistorial.adapter = historialAdapter
    }
}