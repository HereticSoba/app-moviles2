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
            Pelicula(1, "El Viaje de Chihiro", "https://m.media-amazon.com/images/M/MV5BM2E2YzcwMTQtNWRlMC00ZGZlLWJhZTEtMDU4ZGIzMWI0NzJmXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg", "Hayao Miyazaki", 2001, 125, 5, "Animación"),
            Pelicula(2, "Mi Vecino Totoro", "https://static.wikia.nocookie.net/studioghibli/images/a/ac/Mi-vecino-totoro_portada_ESP.jpg/revision/latest?cb=20200411042641&path-prefix=es", "Hayao Miyazaki", 1988, 86, 5, "Animación"),
            Pelicula(3, "La Princesa Mononoke", "https://m.media-amazon.com/images/M/MV5BYjc1YjI2OGUtNzgyOC00ZmFiLThkNzgtYTRkNDQ5ZGEwM2I1XkEyXkFqcGc@._V1_.jpg", "Hayao Miyazaki", 1997, 134, 5, "Aventura"),
            Pelicula(4, "El Castillo Ambulante", "https://es.web.img3.acsta.net/img/90/ea/90ea2e07d6c5897672fbd39c73b4d14f.jpg", "Hayao Miyazaki", 2004, 119, 4, "Fantasía"),
            Pelicula(5, "El Padrino", "https://m.media-amazon.com/images/M/MV5BZmNiNzM4MTctODI5YS00MzczLWE2MzktNzY4YmNjYjA5YmY1XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg", "Francis Ford Coppola", 1972, 175, 5, "Drama"),
            Pelicula(6, "Inception", "https://m.media-amazon.com/images/M/MV5BZjhkNjM0ZTMtNGM5MC00ZTQ3LTk3YmYtZTkzYzdiNWE0ZTA2XkEyXkFqcGc@._V1_.jpg", "Christopher Nolan", 2010, 148, 5, "Ciencia Ficción"),
            Pelicula(7, "Interestelar", "https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@._V1_.jpg", "Christopher Nolan", 2014, 169, 5, "Ciencia Ficción"),
            Pelicula(8, "Parásitos", "https://es.web.img3.acsta.net/c_310_420/pictures/19/09/17/17/13/3740579.jpg", "Bong Joon-ho", 2019, 132, 5, "Thriller")
        )

        peliculaAdapter = PeliculaAdapter(this, peliculas) { pelicula ->
            val intent = Intent(this, PeliculaDetalleActivity::class.java)
            intent.putExtra("image",pelicula.image)
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