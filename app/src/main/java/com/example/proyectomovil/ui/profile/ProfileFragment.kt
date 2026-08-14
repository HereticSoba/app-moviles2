package com.example.proyectomovil.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.example.proyectomovil.R
import com.example.proyectomovil.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var tvSaludo: TextView
    private lateinit var tvNumResenas: TextView
    private lateinit var tvNumFavoritos: TextView
    private lateinit var tvNumVistos: TextView
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnMenuProfile: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSaludo = view.findViewById(R.id.tvSaludo)
        tvNumResenas = view.findViewById(R.id.tvNumResenas)
        tvNumFavoritos = view.findViewById(R.id.tvNumFavoritos)
        tvNumVistos = view.findViewById(R.id.tvNumVistos)
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion)
        btnMenuProfile = view.findViewById(R.id.btnmenu_profile)

        val preferencias = requireContext().getSharedPreferences(
            "sesion",
            Context.MODE_PRIVATE
        )

        val nombre = preferencias.getString("nombre", "")
        tvSaludo.text = "Hola, $nombre!"

        val idUsuario = preferencias.getInt("id_usuario", -1)

        val helper = AppDatabaseHelper(requireContext())
        tvNumResenas.text = helper.contarResenasUsuario(idUsuario).toString()
        tvNumFavoritos.text = helper.contarFavoritosUsuario(idUsuario).toString()
        tvNumVistos.text = helper.contarVistosUsuario(idUsuario).toString()

        btnMenuProfile.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.flmain, com.example.proyectomovil.MainFragment())
                .commit()
        }

        btnCerrarSesion.setOnClickListener {
            preferencias.edit().clear().apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }
    }
}