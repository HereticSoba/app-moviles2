package com.example.proyectomovil.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectomovil.R
import com.example.proyectomovil.repository.HistorialRepository
import com.example.proyectomovil.entity.PeliculaVista
import com.google.android.material.button.MaterialButton

class HistorialAdapter(
    private val context: Context,
    private val lista: MutableList<PeliculaVista>
) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialAdapter.HistorialViewHolder, position: Int) {
        val pelicula = lista[position]
        Glide.with(context).load(pelicula.image).into(holder.ivimage)
        holder.tvtitulo.text = pelicula.title
        holder.tvdirector.text = pelicula.director
        holder.tvestreno.text = pelicula.anioEstreno.toString()
        holder.tvduracion.text = "${pelicula.duracionMinutos} min"
        holder.tvcalificacion.text = "${pelicula.calificacion}/5"
        holder.tvcategoria.text = pelicula.categoria
        holder.tvfechaagregado.text = pelicula.fecha_agregado
        holder.btneliminar.setOnClickListener {
            val repository = HistorialRepository(context)
            repository.eliminar_historial(pelicula.id)
            eliminar_cardview(position)
            Toast.makeText(context, "${pelicula.title} Borrado del Historial", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount(): Int = lista.size

    inner class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivimage: ImageView = itemView.findViewById(R.id.ivimagen)
        val tvtitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvdirector: TextView = itemView.findViewById(R.id.tvdirector)
        val tvestreno: TextView = itemView.findViewById(R.id.tvestreno)
        val tvduracion: TextView = itemView.findViewById(R.id.tvduracion)
        val tvcalificacion: TextView = itemView.findViewById(R.id.tvcalificacion)
        val tvcategoria: TextView = itemView.findViewById(R.id.tvcategoria)
        val tvfechaagregado: TextView = itemView.findViewById(R.id.tvfechaagregadatitulo)
        val btneliminar: MaterialButton = itemView.findViewById(R.id.btneliminar)
    }

    fun eliminar_cardview(position: Int) {
        lista.removeAt(position)
        notifyItemRemoved(position)
    }
}