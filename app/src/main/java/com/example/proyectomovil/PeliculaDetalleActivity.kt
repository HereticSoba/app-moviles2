package com.example.proyectomovil

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PeliculaDetalleActivity : AppCompatActivity() {

    private lateinit var tvTitulo: TextView
    private lateinit var tvDirector: TextView
    private lateinit var tvAnio: TextView
    private lateinit var tvDuracion: TextView
    private lateinit var tvCalificacion: TextView
    private lateinit var tvCategoria: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pelicula_detalle)

        tvTitulo = findViewById(R.id.tvDetalleTitulo)
        tvDirector = findViewById(R.id.tvDetalleDirector)
        tvAnio = findViewById(R.id.tvDetalleAnio)
        tvDuracion = findViewById(R.id.tvDetalleDuracion)
        tvCalificacion = findViewById(R.id.tvDetalleCalificacion)
        tvCategoria = findViewById(R.id.tvDetalleCategoria)

        if (intent.extras != null) {
            tvTitulo.text = intent.getStringExtra("titulo")
            tvDirector.text = intent.getStringExtra("director")
            tvAnio.text = intent.getIntExtra("anio", 0).toString()
            tvDuracion.text = "${intent.getIntExtra("duracion", 0)} min"
            tvCalificacion.text = "${intent.getIntExtra("calificacion", 0)}/5"
            tvCategoria.text = intent.getStringExtra("categoria")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
