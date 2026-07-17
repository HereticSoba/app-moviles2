package com.example.proyectomovil.repository

import android.R
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.example.proyectomovil.entity.Favoritos_provisional

class FavoritosRepository(context: Context) {

    private val dbhelper= AppDatabaseHelper(context)

    fun insertar_favoritos_db(favoritos : Favoritos_provisional): Long{
        val db = dbhelper.writableDatabase
        val valores = ContentValues().apply {
            put("titulo_pelicula",favoritos.title)
            put("imagen",favoritos.image)
            put("director",favoritos.director)
            put("estreno",favoritos.anioEstreno)
            put("duracion",favoritos.duracionMinutos)
            put("calificacion",favoritos.calificacion)
            put("categoria",favoritos.categoria)
            put("fecha_agregado",favoritos.fecha_agregado)
        }
        val id =db.insert("favoritos_provisional",null,valores)
        db.close()
        return id
    }

    fun listar_favoritos() : List<Favoritos_provisional>{
        val db = dbhelper.readableDatabase
        val lista = mutableListOf<Favoritos_provisional>()
        val cursor : Cursor= db.rawQuery("Select * from favoritos_provisional",null)
        while (cursor.moveToNext()){
            lista.add(
                Favoritos_provisional(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("titulo_pelicula")),
                    anioEstreno = cursor.getInt(cursor.getColumnIndexOrThrow("estreno")),
                    calificacion = cursor.getInt(cursor.getColumnIndexOrThrow("calificacion")),
                    categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria")),
                    director = cursor.getString(cursor.getColumnIndexOrThrow("director")),
                    duracionMinutos = cursor.getInt(cursor.getColumnIndexOrThrow("duracion")),
                    fecha_agregado = cursor.getString(cursor.getColumnIndexOrThrow("fecha_agregado")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }

    fun validar_insert(titulo : String) : Boolean{
        val db = dbhelper.readableDatabase
        val cursor: Cursor = db.rawQuery("Select titulo_pelicula from favoritos_provisional where titulo_pelicula = ?",arrayOf(titulo))
        if(cursor.moveToFirst()){
            return false
        }
        else{return true}
    }
}