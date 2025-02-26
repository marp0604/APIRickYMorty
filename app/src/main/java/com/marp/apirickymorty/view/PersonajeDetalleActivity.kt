package com.marp.apirickymorty.view

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.marp.apirickymorty.R
import com.squareup.picasso.Picasso

/**
 * Actividad que muestra los detalles de un personaje especifico.
 * Incluye informacion como el nombre, imagen, estado, especie, genero y origen del personaje.
 *
 * @author Miguel Angel Ramirez Perez
 */
class PersonajeDetalleActivity : AppCompatActivity() {

    /**
     * Metodo que se llama cuando la actividad se crea.
     * Configura la vista, obtiene los datos del personaje pasados como extras y los muestra en la interfaz.
     * Tambien configura el boton de volver.
     *
     * @param savedInstanceState Estado anterior de la actividad, si lo hay.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje_detalle)

        val personajeNombre = intent.getStringExtra("PERSONAJE_NOMBRE")
        val personajeImagen = intent.getStringExtra("PERSONAJE_IMAGEN")
        val personajeEstado = intent.getStringExtra("PERSONAJE_ESTADO")
        val personajeEspecie = intent.getStringExtra("PERSONAJE_ESPECIE")
        val personajeGenero = intent.getStringExtra("PERSONAJE_GENERO")
        val personajeOrigen = intent.getStringExtra("PERSONAJE_ORIGEN")

        val tvNombre = findViewById<TextView>(R.id.tvNombrePersonaje)
        val ivImagen = findViewById<ImageView>(R.id.ivImagenPersonaje)
        val tvEstado = findViewById<TextView>(R.id.tvEstado)
        val tvEspecie = findViewById<TextView>(R.id.tvEspecie)
        val tvGenero = findViewById<TextView>(R.id.tvGenero)
        val tvOrigen = findViewById<TextView>(R.id.tvOrigen)

        tvNombre.text = personajeNombre
        Picasso.get().load(personajeImagen).into(ivImagen)
        tvEstado.text = personajeEstado
        tvEspecie.text = personajeEspecie
        tvGenero.text = personajeGenero
        tvOrigen.text = personajeOrigen

        val btnVolver: ImageButton = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }
    }
}