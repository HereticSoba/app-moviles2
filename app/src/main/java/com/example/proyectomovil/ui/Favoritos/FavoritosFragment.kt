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
        val peliculas = listOf(
            Pelicula(1, "El Viaje de Chihiro", "https://picsum.photos/seed/hist1/200/300", "Hayao Miyazaki", 2001, 125, 5, "Animación"),
            Pelicula(3, "La Princesa Mononoke", "https://picsum.photos/seed/hist2/200/300", "Hayao Miyazaki", 1997, 134, 5, "Aventura"),
            Pelicula(5, "El Padrino", "https://picsum.photos/seed/hist3/200/300", "Francis Ford Coppola", 1972, 175, 5, "Drama")
        )
        rvfavoritos = view.findViewById(R.id.rvfavoritos)
        rvfavoritos.layoutManager = LinearLayoutManager(requireContext())
        favoritosadapter = FavoritosAdapter(requireContext() , peliculas)
        rvfavoritos.adapter =favoritosadapter
    }


}