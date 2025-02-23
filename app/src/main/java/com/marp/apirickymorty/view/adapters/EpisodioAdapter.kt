package com.marp.apirickymorty.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marp.apirickymorty.R
import com.marp.apirickymorty.model.Episodio

class EpisodioAdapter(private var listaEpisodios: List<Episodio>) :
    RecyclerView.Adapter<EpisodioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.nombreEpisodio)
        val numero = view.findViewById<TextView>(R.id.numeroEpisodio)
        val fecha = view.findViewById<TextView>(R.id.fechaEpisodio)

        fun bind(episodio: Episodio) {
            nombre.text = episodio.name
            numero.text = episodio.episode
            fecha.text = episodio.air_date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episodio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaEpisodios[position])
    }

    override fun getItemCount(): Int = listaEpisodios.size

    fun actualizarLista(nuevaLista: List<Episodio>) {
        listaEpisodios = nuevaLista
        notifyDataSetChanged()
    }
}
