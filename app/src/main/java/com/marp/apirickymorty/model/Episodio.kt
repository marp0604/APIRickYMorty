package com.marp.apirickymorty.model

data class Episodio(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
)

