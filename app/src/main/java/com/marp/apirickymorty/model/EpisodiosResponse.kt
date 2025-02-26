package com.marp.apirickymorty.model

/**
 * Representa una respuesta de la API que contiene una lista de episodios.
 *
 * @property results Una lista de objetos [Episodio] que representa los episodios obtenidos de la API.
 *
 * @author Miguel Angel Ramirez Perez
 */

data class EpisodiosResponse(
    val results: List<Episodio>
)