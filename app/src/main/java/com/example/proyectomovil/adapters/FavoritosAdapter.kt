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
import com.example.proyectomovil.entity.Favoritos_provisional
import com.example.proyectomovil.entity.Pelicula
import com.example.proyectomovil.repository.FavoritosRepository
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text

class FavoritosAdapter(private val context : Context, private val lista : MutableList<Favoritos_provisional>) : RecyclerView.Adapter<FavoritosAdapter.FavoritosViewholder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritosViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorito,parent,false)
        return FavoritosViewholder(view)
    }

    override fun onBindViewHolder(
        holder: FavoritosViewholder,
        position: Int
    ) {
        val pelicula =lista[position]
        Glide.with(context).load(pelicula.image).into(holder.ivimage)
        holder.tvtitulo.text = pelicula.title
        holder.tvestreno.text = pelicula.anioEstreno.toString()
        holder.tvduracion.text = pelicula.duracionMinutos.toString()
        holder.tvcalificacion.text = pelicula.calificacion.toString()
        holder.tvcategoria.text = pelicula.categoria
        holder.tvfechaagregado.text = pelicula.fecha_agregado.toString()
        holder.btneliminar.setOnClickListener {
            val repository = FavoritosRepository(context)
            repository.eliminar_favorito(pelicula.id)
            eliminar_cardview(position)
            Toast.makeText(context,"${pelicula.title} Eliminada con exito",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    inner class FavoritosViewholder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val ivimage : ImageView =itemview.findViewById<ImageView>(R.id.ivimagen)
        val tvtitulo : TextView = itemview.findViewById<TextView>(R.id.tvTitulo)
        val tvestreno : TextView = itemview.findViewById<TextView>(R.id.tvestreno)
        val tvduracion : TextView = itemview.findViewById<TextView>(R.id.tvduracion)
        val tvcalificacion : TextView = itemview.findViewById<TextView>(R.id.tvcalificacion)
        val tvcategoria : TextView = itemview.findViewById<TextView>(R.id.tvcategoria)
        val tvfechaagregado : TextView = itemview.findViewById<TextView>(R.id.tvfechamostrar)
        val btneliminar : MaterialButton = itemview.findViewById<MaterialButton>(R.id.btneliminar)
    }

    fun eliminar_cardview(posicion : Int){
        lista.removeAt(posicion)
        notifyItemRemoved(posicion)
    }


}