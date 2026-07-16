package com.example.proyectomovil.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.proyectomovil.entity.Usuario

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
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        versionvieja: Int,
        versionnueva: Int
    ) {
        if (versionvieja < 2){
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS usuario(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                correo TEXT NOT NULL UNIQUE,
                contrasena TEXT NOT NULL
                )
            """.trimIndent())
        }
        db.execSQL("DROP TABLE IF EXISTS favoritos_provisional")
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
}