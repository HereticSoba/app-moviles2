package com.example.proyectomovil.ui.Historial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.R
import com.example.proyectomovil.adapters.PeliculaAdapter
import com.example.proyectomovil.entity.Pelicula

class HistorialActivity : AppCompatActivity() {

    private lateinit var rvHistorial: RecyclerView
    private lateinit var historialAdapter: PeliculaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial)

        rvHistorial = findViewById(R.id.rvHistorial)
        rvHistorial.layoutManager = LinearLayoutManager(this)

        val peliculasVistas = listOf(
            Pelicula(1, "El Viaje de Chihiro", "https://picsum.photos/seed/hist1/200/300", "Hayao Miyazaki", 2001, 125, 5, "Animación"),
            Pelicula(3, "La Princesa Mononoke", "https://picsum.photos/seed/hist2/200/300", "Hayao Miyazaki", 1997, 134, 5, "Aventura"),
            Pelicula(5, "El Padrino", "https://picsum.photos/seed/hist3/200/300", "Francis Ford Coppola", 1972, 175, 5, "Drama")
        )

        historialAdapter = PeliculaAdapter(this, peliculasVistas) { pelicula ->
            // Por ahora sin acción al hacer click en historial
        }
        rvHistorial.adapter = historialAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
