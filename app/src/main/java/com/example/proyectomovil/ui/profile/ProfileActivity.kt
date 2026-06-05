package com.example.proyectomovil.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectomovil.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnMisFavoritos.setOnClickListener {

        }


        binding.btnConfiguracion.setOnClickListener {

        }


        binding.btnCerrarSesion.setOnClickListener {

        }
    }
}