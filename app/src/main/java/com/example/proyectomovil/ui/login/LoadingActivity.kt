package com.example.proyectomovil

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectomovil.repository.CatalogoRepository

class LoadingActivity : AppCompatActivity() {

    private var firebaseListo = false
    private var tiempoListo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_loading)


        CatalogoRepository.obtenerPeliculasFirebase {
            firebaseListo = true
            verificarCarga()
        }


        Handler(Looper.getMainLooper()).postDelayed({
            tiempoListo = true
            verificarCarga()
        }, 2800)
    }

    private fun verificarCarga() {

        if (firebaseListo && tiempoListo) {

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()
        }
    }
}