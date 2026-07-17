package com.example.proyectomovil.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.proyectomovil.entity.Usuario
import com.example.proyectomovil.entity.Pelicula
import com.example.proyectomovil.entity.Resena

class AppDatabaseHelper(context : Context) : SQLiteOpenHelper(context, "peliculas.db",null,2) {
    companion object {
        const val TABLE_USUARIO = "usuario"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_CORREO = "correo"
        const val COLUMN_CONTRASENA = "contrasena"
    }

    override fun onCreate(db: SQLiteDatabase) {
        //pongan los nombres de las base de datos en minuscula para menos problemas - Han
        db.execSQL(
            """
            create table favoritos_provisional(
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                titulo_pelicula TEXT,
                imagen TEXT,
                director TEXT,
                estreno INTEGER,
                duracion INTEGER,
                calificacion INTEGER,
                categoria TEXT,
                fecha_agregado TEXT
            )
        """.trimIndent()
        )
        db.execSQL(
            """
            create table usuario(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            correo TEXT NOT NULL UNIQUE,
            contrasena TEXT NOT NULL
            )
        """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE pelicula(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            titulo TEXT,
            imagen TEXT,
            director TEXT,
            categoria TEXT
            )
        """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE resena(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_pelicula INTEGER,
            comentario TEXT,
            calificacion REAL
            )
        """.trimIndent()
        )
        db.execSQL("INSERT INTO pelicula (titulo,imagen,director,categoria) VALUES ('El Viaje de Chihiro','https://m.media-amazon.com/images/M/MV5BM2E2YzcwMTQtNWRlMC00ZGZlLWJhZTEtMDU4ZGIzMWI0NzJmXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg','Hayao Miyazaki','Animación')")
        db.execSQL("INSERT INTO pelicula (titulo,imagen,director,categoria) VALUES ('El Padrino','https://m.media-amazon.com/images/M/MV5BZmNiNzM4MTctODI5YS00MzczLWE2MzktNzY4YmNjYjA5YmY1XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg','Francis Ford Coppola','Drama')")
        db.execSQL("INSERT INTO pelicula (titulo,imagen,director,categoria) VALUES ('Inception','https://m.media-amazon.com/images/M/MV5BZjhkNjM0ZTMtNGM5MC00ZTQ3LTk3YmYtZTkzYzdiNWE0ZTA2XkEyXkFqcGc@._V1_.jpg','Christopher Nolan','Ciencia Ficción')")
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        versionvieja: Int,
        versionnueva: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS favoritos_provisional")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS pelicula")
        db.execSQL("DROP TABLE IF EXISTS resena")
        onCreate(db)
    }

    fun insertarUsuario(usuario: Usuario): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, usuario.nombre)
            put(COLUMN_CORREO, usuario.correo)
            put(COLUMN_CONTRASENA, usuario.contrasena)
        }
        val resultado = db.insert(TABLE_USUARIO, null, values)
        db.close()
        return resultado != -1L
    }

    fun existeCorreo(correo: String): Boolean {
        val db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM $TABLE_USUARIO WHERE $COLUMN_CORREO = ?", arrayOf(correo))
        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }

    fun obtenerUsuario(correo: String, contrasena: String): Usuario? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USUARIO WHERE $COLUMN_CORREO = ? AND $COLUMN_CONTRASENA = ?",
            arrayOf(correo, contrasena)
        )
        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = Usuario(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                correo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO)),
                contrasena = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTRASENA))
            )
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun obtenerPeliculas(query: String): List<Pelicula> {
        val db = this.readableDatabase
        val lista = mutableListOf<Pelicula>()
        val cursor = if (query.isEmpty()) {
            db.rawQuery("SELECT * FROM pelicula", null)
        } else {
            db.rawQuery("SELECT * FROM pelicula WHERE titulo LIKE ?", arrayOf("%$query%"))
        }
        while (cursor.moveToNext()) {
            lista.add(
                Pelicula(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("imagen")),
                    director = cursor.getString(cursor.getColumnIndexOrThrow("director")),
                    anioEstreno = 0,
                    duracionMinutos = 0,
                    calificacion = 0,
                    categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }

    fun insertarResena(idPelicula: Int, comentario: String, calificacion: Float): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id_pelicula", idPelicula)
            put("comentario", comentario)
            put("calificacion", calificacion)
        }
        val resultado = db.insert("resena", null, values)
        db.close()
        return resultado != -1L
    }

    fun listarResenas(idPelicula: Int): List<Resena> {
        val db = this.readableDatabase
        val lista = mutableListOf<Resena>()
        val cursor = db.rawQuery(
            "SELECT * FROM resena WHERE id_pelicula = ?", arrayOf(idPelicula.toString())
        )
        while (cursor.moveToNext()) {
            lista.add(
                Resena(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    idPelicula = cursor.getInt(cursor.getColumnIndexOrThrow("id_pelicula")),
                    comentario = cursor.getString(cursor.getColumnIndexOrThrow("comentario")),
                    calificacion = cursor.getFloat(cursor.getColumnIndexOrThrow("calificacion"))
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }

    fun insertarHistorial(idUsuario: Int, idPelicula: Int, fecha: String):Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("idUsuario", idUsuario)
            put("idPelicula", idPelicula)
            put("fecha_agregado", fecha)
        }
        val resultado = db.insert("historial", null, values)
        db.close()
        return resultado != -1L
    }
}