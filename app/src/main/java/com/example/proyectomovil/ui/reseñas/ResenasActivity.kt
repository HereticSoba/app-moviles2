package com.example.proyectomovil.ui.reseñas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.R
import com.example.proyectomovil.adapters.ResenaAdapter
import com.example.proyectomovil.entity.Resena

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

        val listaResenas = listOf(
            Resena(
                "Han Yan","Excelente película, chevere.",5),
            Resena(
                "Maryori Solis","Buena historia, lo volvería a ver.",4),
            Resena(
                "Robert Soto","Piola xd.",4),
            Resena(
                "Diego Solorzano","Merece un Oscar :v.",5            ),
            Resena("Bryant Yacila","Tremenda obra maestra 20/10 y god.",5)
        )
        rvResenas.layoutManager = LinearLayoutManager(this)
        rvResenas.adapter = ResenaAdapter(listaResenas)
    }
}