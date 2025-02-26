package com.marp.apirickymorty.model

/**
 * Representa una temporada de la serie "Rick and Morty".
 *
 * @property season El numero de temporada.
 * @property episodes El numero total de episodios en la temporada.
 *
 * @author Miguel Angel Ramirez Perez
 */

data class Temporadas(
    val season: Int,
    val episodes: Int
)
