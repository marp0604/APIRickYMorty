package com.marp.apirickymorty.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.marp.apirickymorty.R
import com.marp.apirickymorty.data.EpisodioService
import com.marp.apirickymorty.databinding.ActivityEpisodioDetalleBinding
import com.marp.apirickymorty.model.Personaje
import com.marp.apirickymorty.view.adapters.PersonajeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Actividad que muestra los detalles de un episodio especifico.
 * Incluye informacion como el nombre, fecha de emision y una lista de personajes asociados al episodio.
 *
 * @author Miguel Angel Ramirez Perez
 */
class EpisodioDetalleActivity : AppCompatActivity() {

    private lateinit var b: ActivityEpisodioDetalleBinding
    private lateinit var personajeAdapter: PersonajeAdapter
    private var episodioId: Int = 0

    /**
     * Metodo que se llama cuando la actividad se crea.
     * Configura la vista, inicializa el RecyclerView, carga los detalles del episodio y configura el boton de volver.
     *
     * @param savedInstanceState Estado anterior de la actividad, si lo hay.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEpisodioDetalleBinding.inflate(layoutInflater)
        setContentView(b.root)

        episodioId = intent.getIntExtra("EPISODIO_ID", 0)
        val episodioNombre = intent.getStringExtra("EPISODIO_NOMBRE")
        val episodioFecha = intent.getStringExtra("EPISODIO_FECHA")

        b.tituloEpisodio.text = episodioNombre
        b.fechaEpisodio.text = episodioFecha

        configurarRecyclerView()
        cargarPersonajes()

        val btnVolver: ImageButton = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }
    }

    /**
     * Configura el RecyclerView para mostrar la lista de personajes asociados al episodio.
     * Define un adapter personalizado y un listener para manejar los clics en los personajes.
     */
    private fun configurarRecyclerView() {
        personajeAdapter = PersonajeAdapter(emptyList()) { personaje ->
            val intent = Intent(this, PersonajeDetalleActivity::class.java).apply {
                putExtra("PERSONAJE_ID", personaje.id)
                putExtra("PERSONAJE_NOMBRE", personaje.name)
                putExtra("PERSONAJE_IMAGEN", personaje.image)
                putExtra("PERSONAJE_ESTADO", personaje.status)
                putExtra("PERSONAJE_ESPECIE", personaje.species)
                putExtra("PERSONAJE_GENERO", personaje.gender)
                putExtra("PERSONAJE_ORIGEN", personaje.origin?.name ?: "Desconocido")
            }
            startActivity(intent)
        }

        b.recyclerPersonajes.layoutManager = LinearLayoutManager(this)
        b.recyclerPersonajes.adapter = personajeAdapter
    }

    /**
     * Crea y devuelve una instancia de Retrofit configurada para hacer solicitudes a la API de Rick and Morty.
     *
     * @return Instancia de Retrofit.
     */
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Carga los personajes asociados al episodio desde la API de "Rick and Morty" y los muestra en el RecyclerView.
     * Este metodo realiza una solicitud asincronica utilizando corrutinas para obtener los personajes asociados al episodio seleccionado
     * y actualiza el adapter del RecyclerView con los datos recibidos. Si no hay personajes o ocurre un error, se muestra
     * un mensaje al usuario.
     */
    private fun cargarPersonajes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = getRetrofit()
                    .create(EpisodioService::class.java)
                    .getEpisodioPorId(episodioId)

                val personajeID = respuesta.characters.map { url ->
                    url.split("/").last().toInt()
                }

                val listaPersonajes = personajeID.map { id ->
                    async(Dispatchers.IO) {
                        getRetrofit()
                            .create(EpisodioService::class.java)
                            .getPersonaje(id)
                    }
                }

                val personajes = listaPersonajes.awaitAll()

                withContext(Dispatchers.Main) {
                    personajeAdapter.actualizarLista(personajes)
                }
            } catch (e: Exception) {
                Toast.makeText(this@EpisodioDetalleActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}