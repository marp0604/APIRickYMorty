package com.marp.apirickymorty.data

import com.marp.apirickymorty.model.Episodio
import com.marp.apirickymorty.model.EpisodiosResponse
import com.marp.apirickymorty.model.Personaje
import com.marp.apirickymorty.model.TemporadasResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Interfaz que define los metodos para interactuar con la API de Rick and Morty.
 * Proporciona metodos para obtener temporadas, episodios por temporada, detalles de un episodio especifico y detalles de un personaje.
 *
 * @author Miguel Angel Ramirez Perez
 */

interface EpisodioService {

    /**
     * Obtiene las temporadas disponibles desde la API de Rick and Morty.
     *
     * @return Un objeto [TemporadasResponse] que contiene la lista de temporadas.
     */
    @GET("episode/?episode=E01")
    suspend fun getTemporadas(): TemporadasResponse

    /**
     * Obtiene una lista de episodios correspondientes a una temporada especifica.
     *
     * @param temporada El numero de temporada.
     * @return Un objeto [EpisodiosResponse] que contiene la lista de episodios de la temporada especifica.
     */
    @GET("episode")
    suspend fun getEpisodiosPorTemporada(@Query("episode") temporada: String): EpisodiosResponse

    /**
     * Obtiene los detalles de un episodio especifico por su ID.
     *
     * @param episodioId El ID del episodio.
     * @return Un objeto [Episodio] que contiene los detalles del episodio especifico.
     */
    @GET("episode/{id}")
    suspend fun getEpisodioPorId(@Path("id") episodioId: Int): Episodio

    /**
     * Obtiene los detalles de un personaje especifico por su ID.
     *
     * @param id El ID del personaje.
     * @return Un objeto [Personaje] que contiene los detalles del personaje especificado.
     */
    @GET("character/{id}")
    suspend fun getPersonaje(@Path("id") id: Int): Personaje
}