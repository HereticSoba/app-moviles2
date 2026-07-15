package com.example.proyectomovil.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovil.R
import com.example.proyectomovil.entity.Resena

class ResenaAdapter(
    private val listaResenas: List<Resena>
    ) : RecyclerView.Adapter<ResenaAdapter.ResenaViewHolder>() {

        class ResenaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtComentario: TextView = itemView.findViewById(R.id.txtComentario)
            val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResenaViewHolder {
            val vista = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_resena, parent, false)
            return ResenaViewHolder(vista)
        }

        override fun onBindViewHolder(holder: ResenaViewHolder, position: Int) {
            val resena = listaResenas[position]
            holder.txtComentario.text = resena.comentario
            holder.ratingBar.rating = resena.calificacion
        }

        override fun getItemCount(): Int {
            return listaResenas.size
        }
    }