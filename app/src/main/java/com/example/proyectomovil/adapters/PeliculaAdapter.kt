package com.example.proyectomovil.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectomovil.Data.AppDatabaseHelper
import com.example.proyectomovil.R
import com.example.proyectomovil.entity.Favoritos_provisional
import com.example.proyectomovil.entity.Pelicula
import com.example.proyectomovil.repository.FavoritosRepository
import com.example.proyectomovil.ui.resenas.ResenasActivity

class PeliculaAdapter(
    private val context: Context,
    private val lista: List<Pelicula>,
    private val onItemClick: (Pelicula) -> Unit
) : RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

    private val dbHelper = AppDatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = lista[position]
        Glide.with(context).load(pelicula.image).into(holder.ivimagen)
        holder.tvTitulo.text = pelicula.title
        holder.tvDirector.text = pelicula.director
        holder.tvEstreno.text = pelicula.anioEstreno.toString()
        holder.tvDuracion.text = "${pelicula.duracionMinutos} min"
        holder.tvCalificacion.text = "${pelicula.calificacion}/5"
        holder.tvCategoria.text = pelicula.categoria

        holder.itemView.setOnClickListener {
            onItemClick(pelicula)
        }

        holder.itemView.setOnLongClickListener {
            val dialogview = LayoutInflater.from(context).inflate(R.layout.dialog_opciones,null)
            val dialog = AlertDialog.Builder(context).setView(dialogview).create()
            val btncancelar = dialogview.findViewById<Button>(R.id.btncancelar)
            val btnresena = dialogview.findViewById<Button>(R.id.btnreseña)
            val btnfavorito = dialogview.findViewById<Button>(R.id.btnfavorito)
            val btnvisto = dialogview.findViewById<Button>(R.id.btnvisto)
            btncancelar.setOnClickListener {
                dialog.dismiss()
            }
            btnresena.setOnClickListener {
                val intent = Intent(context, ResenasActivity::class.java)
                intent.putExtra("id_pelicula",pelicula.id) // para cargar nuestras reseñas
                intent.putExtra("titulo_pelicula",pelicula.title) // carga el titulo de la pelicula en la lista de reseña
                context.startActivity(intent)
                dialog.dismiss()
            }

            // fecha en la que se agrego
            val fechaagregada = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())

            btnfavorito.setOnClickListener {
                val preferencias = context.getSharedPreferences("sesion", android.content.Context.MODE_PRIVATE)
                val idUsuario = preferencias.getInt("id_usuario", -1)

                if (idUsuario == -1) {
                    Toast.makeText(context, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    return@setOnClickListener
                }

                val fechaagregada = java.text.SimpleDateFormat(
                    "yyyy-MM-dd",
                    java.util.Locale.getDefault()
                ).format(java.util.Date())
                val favoritoreposiroty = FavoritosRepository(context)
                if(favoritoreposiroty.validar_insert(pelicula.title)==false){
                    dialog.dismiss()
                    Toast.makeText(context,"${pelicula.title} ya esta añadida",Toast.LENGTH_SHORT).show()
                }
                else{
                    val idfavorito = favoritoreposiroty.insertar_favoritos_db(
                        Favoritos_provisional(
                            id = pelicula.id,
                            idUsuario = idUsuario,
                            anioEstreno = pelicula.anioEstreno,
                            calificacion = pelicula.calificacion,
                            categoria = pelicula.categoria,
                            director = pelicula.director,
                            duracionMinutos = pelicula.duracionMinutos,
                            image = pelicula.image,
                            title = pelicula.title,
                            fecha_agregado = fechaagregada
                        )
                    )
                    dialog.dismiss()
                    Toast.makeText(context,"${pelicula.title} añadida con exito",Toast.LENGTH_SHORT).show()
                }

            }
            btnvisto.setOnClickListener {
                val preferencias = context.getSharedPreferences("sesion", android.content.Context.MODE_PRIVATE)
                val idUsuario = preferencias.getInt("id_usuario", -1)

                if (idUsuario == -1) {
                    Toast.makeText(context, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    return@setOnClickListener
                }

                val exito = dbHelper.insertarHistorial(idUsuario, pelicula.id, fechaagregada) // corrrecion pendiente -- CORRECION HECHA
                if(exito){
                Toast.makeText(context,"${pelicula.title} marcada como visto", Toast.LENGTH_SHORT).show()}
                else{
                    Toast.makeText(context, "Error al guardar el historial", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            dialog.show()
            true
        }

    }

    override fun getItemCount(): Int = lista.size

    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivimagen: ImageView = itemView.findViewById(R.id.ivimagen)
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvDirector: TextView = itemView.findViewById(R.id.tvdirector)
        val tvEstreno: TextView = itemView.findViewById(R.id.tvestreno)
        val tvDuracion: TextView = itemView.findViewById(R.id.tvduracion)
        val tvCalificacion: TextView = itemView.findViewById(R.id.tvcalificacion)
        val tvCategoria: TextView = itemView.findViewById(R.id.tvcategoria)
    }
}
