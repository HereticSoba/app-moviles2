package com.example.proyectomovil.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.proyectomovil.entity.Usuario
import com.example.proyectomovil.entity.Pelicula
import com.example.proyectomovil.entity.Resena
import com.example.proyectomovil.entity.PeliculaVista

class AppDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "peliculas.db", null, 7) {
    companion object {
        const val TABLE_USUARIO = "usuario"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_CORREO = "correo"
        const val COLUMN_CONTRASENA = "contrasena"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE usuario(
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
            categoria TEXT,
            anioEstreno INTEGER,
            duracion INTEGER,
            calificacion INTEGER,
            firebaseId TEXT
            )
        """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE favoritos(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            idUsuario INTEGER,
            idPelicula INTEGER,
            fecha_agregado TEXT
            )
        """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE resena(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_pelicula INTEGER,
            idUsuario INTEGER,
            comentario TEXT,
            calificacion REAL
            )
        """.trimIndent()
        )
        db.execSQL(
            """
                CREATE TABLE historial(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                idUsuario INTEGER,
                idPelicula INTEGER,
                fecha_agregado TEXT
                )
            """.trimIndent()
        )
        db.execSQL("INSERT INTO pelicula (titulo,imagen,director,categoria,anioEstreno,duracion,calificacion,firebaseId) VALUES ('El Viaje de Chihiro','https://m.media-amazon.com/images/M/MV5BM2E2YzcwMTQtNWRlMC00ZGZlLWJhZTEtMDU4ZGIzMWI0NzJmXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg','Hayao Miyazaki','Animación',2001,125,5,'')")
        db.execSQL("INSERT INTO pelicula (titulo,imagen,director,categoria,anioEstreno,duracion,calificacion,firebaseId) VALUES ('El Padrino','https://m.media-amazon.com/images/M/MV5BZmNiNzM4MTctODI5YS00MzczLWE2MzktNzY4YmNjYjA5YmY1XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg','Francis Ford Coppola','Drama',1972,175,5,'')")
        db.execSQL("INSERT INTO pelicula (titulo,imagen,director,categoria,anioEstreno,duracion,calificacion,firebaseId) VALUES ('Inception','https://m.media-amazon.com/images/M/MV5BZjhkNjM0ZTMtNGM5MC00ZTQ3LTk3YmYtZTkzYzdiNWE0ZTA2XkEyXkFqcGc@._V1_.jpg','Christopher Nolan','Ciencia Ficción',2010,148,5,'')")
    }

    override fun onUpgrade(db: SQLiteDatabase, versionvieja: Int, versionnueva: Int) {
        db.execSQL("DROP TABLE IF EXISTS favoritos")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS pelicula")
        db.execSQL("DROP TABLE IF EXISTS resena")
        db.execSQL("DROP TABLE IF EXISTS historial")
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
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USUARIO WHERE $COLUMN_CORREO = ?", arrayOf(correo))
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
                    anioEstreno = cursor.getInt(cursor.getColumnIndexOrThrow("anioEstreno")),
                    duracionMinutos = cursor.getInt(cursor.getColumnIndexOrThrow("duracion")),
                    calificacion = cursor.getInt(cursor.getColumnIndexOrThrow("calificacion")),
                    categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }


    fun obtenerOInsertarPelicula(
        titulo: String,
        imagen: String,
        director: String,
        categoria: String,
        anioEstreno: Int,
        duracionMinutos: Int,
        calificacion: Int,
        firebaseId: String
    ): Int {
        val db = this.writableDatabase

        if (firebaseId.isNotEmpty()) {
            val cursor = db.rawQuery("SELECT id FROM pelicula WHERE firebaseId = ?", arrayOf(firebaseId))
            if (cursor.moveToFirst()) {
                val idExistente = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                cursor.close()
                db.close()
                return idExistente
            }
            cursor.close()
        }

        val values = ContentValues().apply {
            put("titulo", titulo)
            put("imagen", imagen)
            put("director", director)
            put("categoria", categoria)
            put("anioEstreno", anioEstreno)
            put("duracion", duracionMinutos)
            put("calificacion", calificacion)
            put("firebaseId", firebaseId)
        }
        val idNuevo = db.insert("pelicula", null, values)
        db.close()
        return idNuevo.toInt()
    }


    fun insertarResena(idPelicula: Int, idUsuario: Int, comentario: String, calificacion: Float): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id_pelicula", idPelicula)
            put("idUsuario", idUsuario)
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

    fun obtenerCalificacionesMaxima(idPelicula: Int): Float {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT MAX(calificacion) as maxCal FROM resena WHERE id_pelicula = ?",
            arrayOf(idPelicula.toString())
        )
        var maxCal = 0f
        if (cursor.moveToFirst()) {
            maxCal = cursor.getFloat(cursor.getColumnIndexOrThrow("maxCal"))
        }
        cursor.close()
        db.close()
        return maxCal
    }

    fun contarResenasUsuario(idUsuario: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) as total FROM resena WHERE idUsuario = ?",
            arrayOf(idUsuario.toString())
        )
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
        }
        cursor.close()
        db.close()
        return total
    }


    fun contarFavoritosUsuario(idUsuario: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) as total FROM favoritos WHERE idUsuario = ?",
            arrayOf(idUsuario.toString())
        )
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
        }
        cursor.close()
        db.close()
        return total
    }


    fun insertarHistorial(idUsuario: Int, idPelicula: Int, fecha: String): Boolean {
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

    fun existeEnHistorial(idUsuario: Int, idPelicula: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT id FROM historial WHERE idUsuario = ? AND idPelicula = ?",
            arrayOf(idUsuario.toString(), idPelicula.toString())
        )
        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()
        return existe
    }

    fun obtenerHistorial(idUsuario: Int): List<PeliculaVista> {
        val db = this.readableDatabase
        val lista = mutableListOf<PeliculaVista>()
        val cursor = db.rawQuery(
            """
                SELECT h.id AS historialId, p.id AS peliculaId, p.titulo, p.imagen, p.director,
                       p.anioEstreno, p.duracion, p.calificacion, p.categoria, h.fecha_agregado
                FROM historial h
                INNER JOIN pelicula p ON h.idPelicula = p.id
                WHERE h.idUsuario = ?
                ORDER BY h.fecha_agregado DESC
            """.trimIndent(),
            arrayOf(idUsuario.toString())
        )
        while (cursor.moveToNext()) {
            lista.add(
                PeliculaVista(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("historialId")),
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

    fun eliminarHistorial(idHistorial: Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM historial WHERE id = ?", arrayOf(idHistorial.toString()))
        db.close()
    }
}