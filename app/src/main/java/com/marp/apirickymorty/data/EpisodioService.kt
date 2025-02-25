package com.marp.apirickymorty.data

import com.marp.apirickymorty.model.Episodio
import com.marp.apirickymorty.model.EpisodiosResponse
import com.marp.apirickymorty.model.Personaje
import com.marp.apirickymorty.model.TemporadasResponse
import com.marp.rickymortyapi.model.EpisodioResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface EpisodioService {

    @GET("episode/?episode=E01")
    suspend fun getTemporadas(): TemporadasResponse

    @GET("episode")
    suspend fun getEpisodiosPorTemporada(@Query("episode") temporada: String): EpisodiosResponse

    @GET("episode/{id}")
    suspend fun getEpisodioPorId(@Path("id") episodioId: Int): Episodio

    @GET("character/{id}")
    suspend fun getPersonaje(@Path("id") id: Int): Personaje
}