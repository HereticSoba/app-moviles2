package com.example.proyectomovil.ui.Favoritos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomovil.MainActivity
import com.example.proyectomovil.R
import com.google.android.material.button.MaterialButton

class FavoritosActivity : AppCompatActivity() {

    private lateinit var btnvolver : MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)
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