package com.example.proyectomovil.repository

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
            put("idUsuario",favoritos.idUsuario)
            put("idPelicula",favoritos.idPelicula)
            put("fecha_agregado",favoritos.fecha_agregado)
        }
        val id =db.insert("favoritos",null,valores)
        db.close()
        return id
    }

    fun listar_favoritos(idUsuario: Int) : MutableList<Favoritos_provisional>{
        val db = dbhelper.readableDatabase
        val lista = mutableListOf<Favoritos_provisional>()
        val cursor : Cursor= db.rawQuery("""
                SELECT f.id AS favoritoId, p.id AS peliculaId, p.titulo, p.imagen, p.director,
                       p.anioEstreno, p.duracion, p.calificacion, p.categoria, f.fecha_agregado
                FROM favoritos f
                INNER JOIN pelicula p ON f.idPelicula = p.id
                WHERE f.idUsuario = ?
            """.trimIndent(),
            arrayOf(idUsuario.toString())
        )
        while (cursor.moveToNext()){
            lista.add(
                Favoritos_provisional(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("favoritoId")),
                    idUsuario = idUsuario,
                    idPelicula = cursor.getInt(cursor.getColumnIndexOrThrow("peliculaId")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("imagen")),
                    director = cursor.getString(cursor.getColumnIndexOrThrow("director")),
                    anioEstreno = cursor.getInt(cursor.getColumnIndexOrThrow("anioEstreno")),
                    duracionMinutos = cursor.getInt(cursor.getColumnIndexOrThrow("duracion")),
                    calificacion = cursor.getInt(cursor.getColumnIndexOrThrow("calificacion")),
                    categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria")),
                    fecha_agregado = cursor.getString(cursor.getColumnIndexOrThrow("fecha_agregado"))
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }

    fun validar_insert( idUsuario: Int, idPelicula: Int ) : Boolean{
        val db = dbhelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
                "SELECT id FROM favoritos WHERE idUsuario = ? AND idPelicula = ?",
            arrayOf(idUsuario.toString(), idPelicula.toString())
        )
        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()
        return !existe
    }

    fun eliminar_favorito(idFavorito: Int) {
        val db = dbhelper.writableDatabase
        db.execSQL("DELETE FROM favoritos WHERE id = ?", arrayOf(idFavorito.toString()))
        db.close()
    }
}