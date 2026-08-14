package com.example.proyectomovil.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomovil.MainActivity
import com.example.proyectomovil.R
import com.example.proyectomovil.ui.registro.RegistroActivity
import android.widget.Toast
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.google.android.material.textfield.TextInputEditText
import com.airbnb.lottie.LottieAnimationView
import com.example.proyectomovil.LoadingActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etClave: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegistro: TextView
    private lateinit var lottieLoading: LottieAnimationView
    private lateinit var appDatabaseHelper: AppDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        appDatabaseHelper = AppDatabaseHelper(this)
        etCorreo = findViewById(R.id.tietCorreo)
        etClave = findViewById(R.id.tietClave)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegistro = findViewById(R.id.tvRegistro)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contrasena = etClave.text.toString().trim()
            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val usuario = appDatabaseHelper.obtenerUsuario(correo, contrasena)
            if (usuario != null) {
                val preferencias = getSharedPreferences("sesion", MODE_PRIVATE)
                preferencias.edit()
                    .putString("nombre", usuario.nombre)
                    .putInt("id_usuario", usuario.id)
                    .apply()
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoadingActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
        tvRegistro.setOnClickListener{
            val intent = Intent(this,RegistroActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}