package com.marp.apirickymorty.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marp.apirickymorty.R
import com.marp.apirickymorty.model.Personaje
import com.squareup.picasso.Picasso

/**
 * Adapter para un RecyclerView que muestra una lista de personajes.
 * Este adapter se encarga de vincular los datos de los personajes con las vistas
 * en cada elemento de la lista.
 *
 * @param listaPersonajes La lista inicial de personajes que se mostrara en el RecyclerView.
 */
class PersonajeAdapter(
    private var listaPersonajes: List<Personaje>
) : RecyclerView.Adapter<PersonajeAdapter.ViewHolder>() {

    /**
     * ViewHolder representa cada elemento de la lista de personajes.
     * Contiene las referencias a las vistas que se actualizaran con los datos del personaje.
     *
     * @param view La vista que representa un elemento de la lista.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombre: TextView = view.findViewById(R.id.nombrePersonaje)
        private val imagen: ImageView = view.findViewById(R.id.imagenPersonaje)

        /**
         * Vincula los datos de un personaje con las vistas correspondientes.
         *
         * @param personaje El objeto Personaje que contiene los datos a mostrar.
         */
        fun bind(personaje: Personaje) {
            nombre.text = personaje.name
            Picasso.get().load(personaje.image).into(imagen)
        }
    }

    /**
     * Crea un nuevo ViewHolder inflando el layout de un elemento de la lista.
     *
     * @param parent El ViewGroup al que se añadira la nueva vista.
     * @param viewType El tipo de vista del nuevo ViewHolder.
     * @return Un nuevo ViewHolder que contiene la vista de un elemento de la lista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje, parent, false)
        return ViewHolder(view)
    }

    /**
     * Vincula los datos de un personaje en una posicion especifica con las vistas del ViewHolder.
     *
     * @param holder El ViewHolder que debe actualizarse con los datos del personaje.
     * @param position La posicion del personaje en la lista.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaPersonajes[position])
    }

    /**
     * Devuelve el numero total de elementos en la lista de personajes.
     *
     * @return El numero de personajes en la lista.
     */
    override fun getItemCount(): Int = listaPersonajes.size

    /**
     * Actualiza la lista de personajes y notifica al RecyclerView que los datos han cambiado.
     *
     * @param nuevaLista La nueva lista de personajes que se mostrara en el RecyclerView.
     */
    fun actualizarLista(nuevaLista: List<Personaje>) {
        listaPersonajes = nuevaLista
        notifyDataSetChanged()
    }
}