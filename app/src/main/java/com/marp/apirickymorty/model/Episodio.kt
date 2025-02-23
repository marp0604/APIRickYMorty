package com.marp.apirickymorty.model

data class Episodio(
    val id: Int,
    val name: String,
    val season: Int,
    val episode: String,
    val air_date: String
)
