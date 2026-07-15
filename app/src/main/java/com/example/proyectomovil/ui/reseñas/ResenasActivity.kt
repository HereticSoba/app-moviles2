package com.example.proyectomovil.ui.reseñas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.R
import com.example.proyectomovil.adapters.ResenaAdapter
import com.example.proyectomovil.database.SQliteHelper
import com.example.proyectomovil.entity.Resena
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.RatingBar

class ResenasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resenas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvResenas = findViewById<RecyclerView>(R.id.rvResenas)
        val etComentario = findViewById<TextInputEditText>(R.id.etComentario)
        val ratingBarNueva = findViewById<RatingBar>(R.id.ratingBarNueva)
        val btnGuardarResena = findViewById<MaterialButton>(R.id.btnGuardarResena)

        val listaResenas = listOf(
            Resena("Han Yan","Excelente película, chevere.",5),
            Resena("Maryori Solis","Buena historia, lo volvería a ver.",4),
            Resena("Robert Soto","Piola xd.",4),
            Resena("Diego Solorzano","Merece un Oscar :v.",5),
            Resena("Bryant Yacila","Tremenda obra maestra 20/10 y god.",5)
        )
        rvResenas.layoutManager = LinearLayoutManager(this)
        rvResenas.adapter = ResenaAdapter(listaResenas)

        val helper = SQliteHelper(this)

        btnGuardarResena.setOnClickListener {
            val comentario = etComentario.text.toString().trim()
            val calificacion = ratingBarNueva.rating
            if (comentario.isEmpty()) {
                Toast.makeText(this, "Escribe un comentario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (calificacion == 0f) {
                Toast.makeText(this, "Selecciona una calificación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val guardado = helper.insertarResena(1, comentario, calificacion)
            if (guardado) {
                Toast.makeText(this, "Reseña guardada", Toast.LENGTH_SHORT).show()
                etComentario.text?.clear()
                ratingBarNueva.rating = 0f
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}