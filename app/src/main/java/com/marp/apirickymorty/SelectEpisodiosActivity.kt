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
        cargarTemporadas()
        val spinner = b.spinnerTemp
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun cargarTemporadas(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val llamada = getRetrofit()
                    .create(EpisodioService::class.java)
                    .getTemporadas()

                val cantidadTemp = llamada.size

                val cantidad = (1..cantidadTemp).toList()

                val spinnerAdapter = ArrayAdapter(this@SelectEpisodiosActivity,
                    android.R.layout.simple_spinner_item,
                    cantidad)

                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                b.spinnerTemp.adapter = spinnerAdapter

            }catch (e: Exception){
                launch(Dispatchers.Main) {
                    Toast.makeText(this@SelectEpisodiosActivity,
                        "Error en el spinner",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}