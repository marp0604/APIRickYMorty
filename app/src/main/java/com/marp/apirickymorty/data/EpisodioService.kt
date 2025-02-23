package com.marp.apirickymorty.data

import com.marp.apirickymorty.model.EpisodiosResponse
import com.marp.apirickymorty.model.TemporadasResponse
import com.marp.rickymortyapi.model.EpisodioResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface EpisodioService {

    @GET
    suspend fun getEpisodios(@Url url: String): List<EpisodioResponse>

    @GET("episode/?episode=E01")
    suspend fun getTemporadas(): TemporadasResponse

    @GET("episode")
    suspend fun getEpisodiosPorTemporada(@Query("episode") temporada: String): EpisodiosResponse

}