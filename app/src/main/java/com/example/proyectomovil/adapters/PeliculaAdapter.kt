package com.example.proyectomovil.adapters

import android.content.Context
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
import com.example.proyectomovil.R
import com.example.proyectomovil.entity.Pelicula

class PeliculaAdapter(
    private val context: Context,
    private val lista: List<Pelicula>,
    private val onItemClick: (Pelicula) -> Unit
) : RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

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
            btncancelar.setOnClickListener {
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
