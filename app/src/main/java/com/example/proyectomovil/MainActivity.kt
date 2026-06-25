package com.example.proyectomovil

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.adapters.PeliculaAdapter
import com.example.proyectomovil.entity.Pelicula
import com.example.proyectomovil.ui.Favoritos.FavoritosActivity
import com.example.proyectomovil.ui.Historial.HistorialActivity
import com.example.proyectomovil.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var rvPeliculas: RecyclerView
    private lateinit var peliculaAdapter: PeliculaAdapter
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPeliculas = findViewById(R.id.rvPeliculas)
        rvPeliculas.layoutManager = LinearLayoutManager(this)

        val peliculas = listOf(
            Pelicula(1, "El Viaje de Chihiro", "https://picsum.photos/seed/peli1/200/300", "Hayao Miyazaki", 2001, 125, 5, "Animación"),
            Pelicula(2, "Mi Vecino Totoro", "https://picsum.photos/seed/peli2/200/300", "Hayao Miyazaki", 1988, 86, 5, "Animación"),
            Pelicula(3, "La Princesa Mononoke", "https://picsum.photos/seed/peli3/200/300", "Hayao Miyazaki", 1997, 134, 5, "Aventura"),
            Pelicula(4, "El Castillo Ambulante", "https://picsum.photos/seed/peli4/200/300", "Hayao Miyazaki", 2004, 119, 4, "Fantasía"),
            Pelicula(5, "El Padrino", "https://picsum.photos/seed/peli5/200/300", "Francis Ford Coppola", 1972, 175, 5, "Drama"),
            Pelicula(6, "Inception", "https://picsum.photos/seed/peli6/200/300", "Christopher Nolan", 2010, 148, 5, "Ciencia Ficción"),
            Pelicula(7, "Interestelar", "https://picsum.photos/seed/peli7/200/300", "Christopher Nolan", 2014, 169, 5, "Ciencia Ficción"),
            Pelicula(8, "Parásitos", "https://picsum.photos/seed/peli8/200/300", "Bong Joon-ho", 2019, 132, 5, "Thriller")
        )

        peliculaAdapter = PeliculaAdapter(this, peliculas) { pelicula ->
            val intent = Intent(this, PeliculaDetalleActivity::class.java)
            intent.putExtra("titulo", pelicula.title)
            intent.putExtra("director", pelicula.director)
            intent.putExtra("anio", pelicula.anioEstreno)
            intent.putExtra("duracion", pelicula.duracionMinutos)
            intent.putExtra("calificacion", pelicula.calificacion)
            intent.putExtra("categoria", pelicula.categoria)
            startActivity(intent)
        }
        rvPeliculas.adapter = peliculaAdapter

        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_inicio
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> true
                R.id.nav_favoritos -> {
                    startActivity(Intent(this, FavoritosActivity::class.java))
                    false
                }
                R.id.nav_historial -> {
                    startActivity(Intent(this, HistorialActivity::class.java))
                    false
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    false
                }
                else -> false
            }
        }
    }
}