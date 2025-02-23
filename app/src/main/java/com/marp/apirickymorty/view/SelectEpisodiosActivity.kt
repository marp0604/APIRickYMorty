package com.marp.apirickymorty.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.marp.apirickymorty.view.adapters.EpisodioAdapter
import com.marp.apirickymorty.databinding.ActivitySelectEpisodiosBinding
import com.marp.apirickymorty.data.EpisodioService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SelectEpisodiosActivity : AppCompatActivity() {

    private lateinit var b: ActivitySelectEpisodiosBinding
    private lateinit var episodioAdapter: EpisodioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySelectEpisodiosBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)

        configurarRecyclerView()
        cargarTemporadas()
        setupSpinnerListener()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun configurarRecyclerView() {
        episodioAdapter = EpisodioAdapter(emptyList())
        b.recyclerEpisodios.layoutManager = LinearLayoutManager(this)
        b.recyclerEpisodios.adapter = episodioAdapter
    }

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

    private fun setupSpinnerListener() {
        b.spinnerTemp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val temporadaSeleccionada = parent.getItemAtPosition(position) as Int
                cargarEpisodiosDeTemporada(temporadaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun cargarEpisodiosDeTemporada(temporada: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val temporadaFormateada = "S0$temporada"

                val respuesta = getRetrofit()
                    .create(EpisodioService::class.java)
                    .getEpisodiosPorTemporada(temporadaFormateada)

                if (respuesta.results.isNotEmpty()) {
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

    private fun mostrarMensaje(mensaje: String) {
        runOnUiThread {
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }
    }
}