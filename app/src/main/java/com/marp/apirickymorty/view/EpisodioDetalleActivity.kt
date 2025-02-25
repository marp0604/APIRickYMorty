package com.marp.apirickymorty.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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


class EpisodioDetalleActivity : AppCompatActivity() {

    private lateinit var b: ActivityEpisodioDetalleBinding
    private lateinit var personajeAdapter: PersonajeAdapter
    private var episodioId: Int = 0

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
    }

    private fun configurarRecyclerView() {
        personajeAdapter = PersonajeAdapter(emptyList())
        b.recyclerPersonajes.layoutManager = LinearLayoutManager(this)
        b.recyclerPersonajes.adapter = personajeAdapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


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
                    personajeAdapter.notifyDataSetChanged()

                }
            } catch (e: Exception) {
                Toast.makeText(this@EpisodioDetalleActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}