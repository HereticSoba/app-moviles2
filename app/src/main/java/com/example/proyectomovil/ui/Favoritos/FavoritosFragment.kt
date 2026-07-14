package com.example.proyectomovil.ui.Favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.R
import com.example.proyectomovil.adapters.FavoritosAdapter
import com.example.proyectomovil.entity.Pelicula
import com.example.proyectomovil.repository.FavoritosRepository


class FavoritosFragment : Fragment() {
    private lateinit var rvfavoritos : RecyclerView
    private lateinit var favoritosadapter : FavoritosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = FavoritosRepository(requireContext())
        val peliculas = repository.listar_favoritos()
        rvfavoritos = view.findViewById(R.id.rvfavoritos)
        rvfavoritos.layoutManager = LinearLayoutManager(requireContext())
        favoritosadapter = FavoritosAdapter(requireContext() , peliculas)
        rvfavoritos.adapter =favoritosadapter
    }


}