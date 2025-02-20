package com.marp.apirickymorty

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marp.apirickymorty.databinding.ActivitySelectEpisodiosBinding
import com.marp.rickymortyapi.model.EpisodioService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SelectEpisodiosActivity : AppCompatActivity() {

    private lateinit var b : ActivitySelectEpisodiosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySelectEpisodiosBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)
        ViewCompat.setOnApplyWindowInsetsListener(b.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("Hola antes de cargar temporadas")
        cargarTemporadas()
        println("Hola despues de cargar temporadas")
    }

    private fun getRetrofit() : Retrofit {
        println("Hola")
        return Retrofit
            .Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        println("Hola retrofit")
    }


    private fun cargarTemporadas() {
        println("Hola desde cargar temporadas")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Llamada para obtener las temporadas de la API
                println("Hola desde try")
                val respuesta = getRetrofit()
                    .create(EpisodioService::class.java)
                    .getTemporadas() // Ahora devuelve un TemporadasResponse

                // Verificar si la respuesta contiene elementos
                if (respuesta.results.isNotEmpty()) {
                    val cantidadTemp = respuesta.results.size
                    val cantidad = (1..cantidadTemp).toList()

                    // Crear y configurar el ArrayAdapter
                    val spinnerAdapter = ArrayAdapter(
                        this@SelectEpisodiosActivity,
                        android.R.layout.simple_spinner_item,
                        cantidad
                    )
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    // Establecer el adapter en el Spinner
                    launch(Dispatchers.Main) {
                        b.spinnerTemp.adapter = spinnerAdapter
                    }

                    println("Temporadas cargadas correctamente")
                } else {
                    println("No se recibieron temporadas")
                }
            } catch (e: Exception) {
                // Mostrar error si algo va mal
                launch(Dispatchers.Main) {
                    Toast.makeText(
                        this@SelectEpisodiosActivity,
                        "Error en el spinner: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}