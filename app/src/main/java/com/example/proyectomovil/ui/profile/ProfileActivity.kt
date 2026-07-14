package com.example.proyectomovil.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectomovil.MainActivity
import com.example.proyectomovil.databinding.ActivityProfileBinding
import com.example.proyectomovil.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferencias = getSharedPreferences("sesion", MODE_PRIVATE)
        val nombre = preferencias.getString("nombre", "")
        binding.tvSaludo.text = "Hola, $nombre!"

        binding.btnmenuProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnCerrarSesion.setOnClickListener {
            val preferencias = getSharedPreferences("sesion", MODE_PRIVATE)
            preferencias.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}