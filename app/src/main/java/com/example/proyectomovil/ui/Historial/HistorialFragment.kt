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
import com.example.proyectomovil.adapters.HistorialAdapter
import com.example.proyectomovil.repository.HistorialRepository

class HistorialFragment : Fragment() {

    private lateinit var rvHistorial: RecyclerView
    private lateinit var historialAdapter: PeliculaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHistorial = view.findViewById(R.id.rvHistorial)
        rvHistorial.layoutManager = LinearLayoutManager(requireContext())

        val preferencias = requireContext().getSharedPreferences(
            "sesion",
            Context.MODE_PRIVATE
        )

        val idUsuario = preferencias.getInt("id_usuario", -1)

        val repository = HistorialRepository(requireContext())

        val peliculasVistas = repository.listar_historial(idUsuario)

        historialAdapter = HistorialAdapter(requireContext(), peliculasVistas)


        rvHistorial.adapter = historialAdapter
    }
}