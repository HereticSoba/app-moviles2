package com.example.proyectomovil.ui.Historial

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.Data.AppDatabaseHelper
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

        val preferencias = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val idUsuario = preferencias.getInt("id_usuario", -1)
        val helper = AppDatabaseHelper(this)
        val peliculasVistas = helper.obtenerHistorial(idUsuario)

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
