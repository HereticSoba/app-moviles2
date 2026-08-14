package com.example.proyectomovil.repository

import android.content.Context
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.example.proyectomovil.entity.PeliculaVista

class HistorialRepository(context: Context) {
    private val dbhelper = AppDatabaseHelper(context)

    fun listar_historial(idUsuario: Int): MutableList<PeliculaVista> {
        return dbhelper.obtenerHistorial(idUsuario).toMutableList()
    }

    fun eliminar_historial(idHistorial: Int) {
        dbhelper.eliminarHistorial(idHistorial)
    }
}