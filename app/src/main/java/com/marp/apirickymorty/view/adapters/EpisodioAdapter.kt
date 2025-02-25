package com.marp.apirickymorty.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marp.apirickymorty.R
import com.marp.apirickymorty.model.Episodio

/**
 * Adapter para un RecyclerView que muestra una lista de episodios.
 * Cada elemento de la lista muestra el nombre y la fecha de emision del episodio.
 * Ademas, maneja los clics en los elementos para ejecutar una accion personalizada.
 *
 * @param listaEpisodios Lista inicial de episodios.
 * @param onItemClick Funcion que se ejecuta cuando se hace clic en un episodio.
 */
class EpisodioAdapter(
    private var listaEpisodios: List<Episodio>,
    private val onItemClick: (Episodio) -> Unit
) : RecyclerView.Adapter<EpisodioAdapter.ViewHolder>() {

    /**
     * ViewHolder que representa cada elemento de la lista de episodios.
     * Contiene referencias a las vistas que muestran el nombre y la fecha del episodio.
     *
     * @param view Vista que representa un elemento de la lista.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombre: TextView = view.findViewById(R.id.nombreEpisodio)
        private val fecha: TextView = view.findViewById(R.id.fechaEpisodio)
        private val numero: TextView = view.findViewById(R.id.numeroEpisodio)

        /**
         * Asocia los datos de un episodio con las vistas del ViewHolder.
         * Tambien configura un listener para manejar los clics en el elemento.
         *
         * @param episodio Episodio a mostrar.
         * @param onItemClick Funcion que se ejecuta al hacer clic en el episodio.
         */
        fun bind(episodio: Episodio, onItemClick: (Episodio) -> Unit) {
            nombre.text = episodio.name
            fecha.text = episodio.air_date
            numero.text = episodio.episode
            itemView.setOnClickListener { onItemClick(episodio) }
        }
    }

    /**
     * Crea un nuevo ViewHolder inflando el layout de un elemento de la lista.
     *
     * @param parent Vista padre en la que se inflara el layout.
     * @return Nuevo ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episodio, parent, false)
        return ViewHolder(view)
    }

    /**
     * Asocia los datos de un episodio en una posicion especifica con un ViewHolder.
     *
     * @param holder ViewHolder que contendrá los datos.
     * @param position Posición del episodio en la lista.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaEpisodios[position], onItemClick)
    }

    /**
     * Retorna el numero de elementos en la lista de episodios.
     *
     * @return Numero de episodios.
     */
    override fun getItemCount(): Int = listaEpisodios.size

    /**
     * Actualiza la lista de episodios y notifica al RecyclerView que los datos han cambiado.
     *
     * @param nuevaLista Nueva lista de episodios a mostrar.
     */
    fun actualizarLista(nuevaLista: List<Episodio>) {
        listaEpisodios = nuevaLista
        notifyDataSetChanged()
    }
}