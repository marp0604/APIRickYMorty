package com.marp.rickymortyapi.model

import retrofit2.http.GET
import retrofit2.http.Url

interface EpisodioService {

    @GET
    suspend fun getEpisodios(@Url url: String): List<EpisodioResponse>

    @GET("episode/?episode=E01")
    suspend fun getTemporadas (): List<EpisodioResponse>

}