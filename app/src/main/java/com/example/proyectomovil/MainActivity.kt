package com.example.proyectomovil

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomovil.ui.Favoritos.FavoritosFragment
import com.example.proyectomovil.ui.Historial.HistorialActivity
import com.example.proyectomovil.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        bottomNavigation = findViewById(R.id.bottomNavigation)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.flmain, MainFragment()).commit()
        }
        bottomNavigation.selectedItemId = R.id.nav_inicio
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    supportFragmentManager.beginTransaction().replace(R.id.flmain, MainFragment()).commit()
                    true
                }
                R.id.nav_favoritos -> {
                    supportFragmentManager.beginTransaction().replace(R.id.flmain,
                        FavoritosFragment()
                    ).commit()
                    true
                }
                R.id.nav_historial -> {
                    startActivity(Intent(this, HistorialActivity::class.java))
                    false
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    false
                }
                else -> false
            }
        }

    }
}