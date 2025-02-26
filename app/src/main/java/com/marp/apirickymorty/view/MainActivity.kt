package com.marp.apirickymorty.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marp.apirickymorty.databinding.ActivityMainBinding

/**
 * Actividad principal de la aplicacion.
 * Esta actividad sirve como punto de entrada y proporciona una interfaz para navegar a otras actividades,
 * como la seleccion de episodios.
 *
 * @author Miguel Angel Ramirez Perez
 */
class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    /**
     * Metodo que se llama cuando la actividad se crea.
     * Configura la vista, habilita el diseño edge-to-edge y configura un listener para el boton de temporadas.
     *
     * @param savedInstanceState Estado anterior de la actividad, si lo hay.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(b.root)

        ViewCompat.setOnApplyWindowInsetsListener(b.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btTemporadas = b.btTemporadas
        btTemporadas.setOnClickListener {
            val intent = Intent(this, SelectEpisodiosActivity::class.java)
            startActivity(intent)
        }
    }
}