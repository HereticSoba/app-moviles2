package com.example.proyectomovil

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.proyectomovil.Data.AppDatabaseHelper
import android.widget.ImageButton

class PeliculaDetalleActivity : AppCompatActivity() {

    private lateinit var ivDetalle: ImageView      // NUEVO
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

        ivDetalle = findViewById(R.id.ivDetalle)          // NUEVO
        tvTitulo = findViewById(R.id.tvDetalleTitulo)
        tvDirector = findViewById(R.id.tvDetalleDirector)
        tvAnio = findViewById(R.id.tvDetalleAnio)
        tvDuracion = findViewById(R.id.tvDetalleDuracion)
        tvCalificacion = findViewById(R.id.tvDetalleCalificacion)
        tvCategoria = findViewById(R.id.tvDetalleCategoria)

        if (intent.extras != null) {
            // NUEVO: carga la imagen con Glide
            val imagenUrl = intent.getStringExtra("image")
            Glide.with(this).load(imagenUrl).into(ivDetalle)

            tvTitulo.text = intent.getStringExtra("titulo")
            tvDirector.text = intent.getStringExtra("director")
            tvAnio.text = intent.getIntExtra("anio", 0).toString()
            tvDuracion.text = "${intent.getIntExtra("duracion", 0)} min"

            val idPelicula = intent.getIntExtra("id", -1)
            val helper = AppDatabaseHelper(this)
            val calificacionMaxima = helper.obtenerCalificacionesMaxima(idPelicula)
            if(calificacionMaxima > 0){
                tvCalificacion.text = "⭐ ${calificacionMaxima}/5"
            }else{
                tvCalificacion.text = "Sin reseñas todavía"
            }
            val btnRegresar = findViewById<ImageButton>(R.id.btnRegresar)
            btnRegresar.setOnClickListener {
                finish()
            }

            tvCategoria.text = intent.getStringExtra("categoria")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}