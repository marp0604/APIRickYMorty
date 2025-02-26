package com.marp.apirickymorty.model

/**
 * Representa un episodio de la serie "Rick and Morty".
 *
 * @property id El ID unico del episodio.
 * @property name El nombre del episodio.
 * @property air_date La fecha en que se emitio el episodio.
 * @property episode El numero de episodio.
 * @property characters Una lista que representa los personajes que aparecen en el episodio.
 *
 * @author Miguel Angel Ramirez Perez
 */

data class Episodio(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
)

