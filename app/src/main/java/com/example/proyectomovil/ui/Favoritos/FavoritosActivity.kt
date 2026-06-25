package com.example.proyectomovil.ui.Favoritos

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.MainActivity
import com.example.proyectomovil.R
import com.example.proyectomovil.adapters.FavoritosAdapter
import com.example.proyectomovil.entity.Pelicula
import com.google.android.material.button.MaterialButton

class FavoritosActivity : AppCompatActivity() {

    private lateinit var btnvolver : MaterialButton
    private lateinit var rvfavoritos : RecyclerView
    private lateinit var favoritosadapter : FavoritosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)
        val peliculas = listOf(
            Pelicula(1, "El Viaje de Chihiro", "https://picsum.photos/seed/hist1/200/300", "Hayao Miyazaki", 2001, 125, 5, "Animación"),
            Pelicula(3, "La Princesa Mononoke", "https://picsum.photos/seed/hist2/200/300", "Hayao Miyazaki", 1997, 134, 5, "Aventura"),
            Pelicula(5, "El Padrino", "https://picsum.photos/seed/hist3/200/300", "Francis Ford Coppola", 1972, 175, 5, "Drama")
        )
        rvfavoritos = findViewById<RecyclerView>(R.id.rvfavoritos)
        rvfavoritos.layoutManager = LinearLayoutManager(this)
        favoritosadapter = FavoritosAdapter(this , peliculas)
        rvfavoritos.adapter =favoritosadapter

        btnvolver = findViewById(R.id.btnvolverfavoritos)
        btnvolver.setOnClickListener {
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}