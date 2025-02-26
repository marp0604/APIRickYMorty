package com.marp.apirickymorty.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.marp.apirickymorty.R
import com.marp.apirickymorty.view.adapters.EpisodioAdapter
import com.marp.apirickymorty.databinding.ActivitySelectEpisodiosBinding
import com.marp.apirickymorty.data.EpisodioService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Actividad que permite al usuario seleccionar una temporada y ver la lista de episodios.
 * Utiliza un Spinner para seleccionar la temporada y un RecyclerView para mostrar los episodios.
 *
 * @author Miguel Angel Ramirez Perez
 */
class SelectEpisodiosActivity : AppCompatActivity() {

    private lateinit var b: ActivitySelectEpisodiosBinding
    private lateinit var episodioAdapter: EpisodioAdapter

    /**
     * Metodo que se llama cuando la actividad se crea.
     * Configura la vista, inicializa el RecyclerView, carga las temporadas y configura el listener del Spinner.
     *
     * @param savedInstanceState Estado anterior de la actividad, si lo hay.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySelectEpisodiosBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)

        configurarRecyclerView()
        cargarTemporadas()
        setupSpinnerListener()

        val btnVolver: ImageButton = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }
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
     * Configura el RecyclerView para mostrar la lista de episodios.
     * Define un adapter personalizado y un listener para manejar los clics en los episodios.
     */
    private fun configurarRecyclerView() {
        episodioAdapter = EpisodioAdapter(emptyList()) { episodio ->
            val intent = Intent(this, EpisodioDetalleActivity::class.java).apply {
                putExtra("EPISODIO_ID", episodio.id)
                putExtra("EPISODIO_NOMBRE", episodio.name)
                putExtra("EPISODIO_FECHA", episodio.air_date)
            }
            startActivity(intent)
        }

        b.recyclerEpisodios.apply {
            layoutManager = LinearLayoutManager(this@SelectEpisodiosActivity)
            adapter = episodioAdapter
        }
    }

    /**
     * Carga las temporadas disponibles desde la API de "Rick and Morty" y las muestra en un Spinner.
     * Este metodo realiza una solicitud asincronica utilizando corrutinas para obtener la lista de temporadas
     * y actualiza el Spinner con los datos recibidos. Si no se reciben temporadas o ocurre un error, se muestra
     * un mensaje al usuario.
     */
    private fun cargarTemporadas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = getRetrofit()
                    .create(EpisodioService::class.java)
                    .getTemporadas()

                if (respuesta.results.isNotEmpty()) {
                    val cantidadTemp = respuesta.results.size
                    val temporadas = (1..cantidadTemp).toList()
                    launch(Dispatchers.Main) {
                        val spinnerAdapter = ArrayAdapter(
                            this@SelectEpisodiosActivity,
                            android.R.layout.simple_spinner_item,
                            temporadas
                        )
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        b.spinnerTemp.adapter = spinnerAdapter
                    }
                } else {
                    mostrarMensaje("No se recibieron temporadas")
                }
            } catch (e: Exception) {
                mostrarMensaje("Error en el spinner: ${e.message}")
            }
        }
    }

    /**
     * Configura el listener del Spinner para cargar los episodios de la temporada seleccionada.
     * Cuando el usuario selecciona una temporada, se llama al metodo cargarEpisodiosDeTemporada para obtener
     * y mostrar los episodios correspondientes.
     */
    private fun setupSpinnerListener() {
        b.spinnerTemp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val temporadaSeleccionada = parent.getItemAtPosition(position) as Int
                cargarEpisodiosDeTemporada(temporadaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    /**
     * Carga los episodios de una temporada especifica desde la API de "Rick and Morty" y los muestra en el RecyclerView.
     * Este metodo realiza una solicitud asincronica utilizando corrutinas para obtener los episodios de la temporada seleccionada
     * y actualiza el adapter del RecyclerView con los datos recibidos. Si no hay episodios o ocurre un error, se muestra
     * un mensaje al usuario.
     *
     * @param temporada Numero de la temporada a cargar.
     */
    private fun cargarEpisodiosDeTemporada(temporada: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val temporadaFormateada = "S0$temporada"
                val respuesta = getRetrofit()
                    .create(EpisodioService::class.java)
                    .getEpisodiosPorTemporada(temporadaFormateada)

                if (respuesta.results.isNotEmpty()) {
                    respuesta.results.forEach { episodio ->
                        println("Episodio recibido: ${episodio.name} - ${episodio.air_date}")
                    }

                    launch(Dispatchers.Main) {
                        episodioAdapter.actualizarLista(respuesta.results)
                    }
                } else {
                    mostrarMensaje("No hay episodios para esta temporada")
                }
            } catch (e: Exception) {
                mostrarMensaje("Error al cargar episodios: ${e.message}")
            }
        }
    }

    /**
     * Muestra un mensaje en forma de Toast en la interfaz de usuario. Este metodo es util para notificar al usuario
     * sobre errores.
     *
     * @param mensaje Mensaje a mostrar en el Toast.
     */
    private fun mostrarMensaje(mensaje: String) {
        runOnUiThread {
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }
    }
}