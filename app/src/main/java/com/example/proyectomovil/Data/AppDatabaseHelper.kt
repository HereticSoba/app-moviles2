package com.example.proyectomovil.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppDatabaseHelper(context : Context) : SQLiteOpenHelper(context, "peliculas.db",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
      //pongan los nombres de las base de datos en minuscula para menos problemas - Han
        db.execSQL("""
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
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        versionvieja: Int,
        versionnueva: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS favoritos_provisional")
        onCreate(db)
    }

}