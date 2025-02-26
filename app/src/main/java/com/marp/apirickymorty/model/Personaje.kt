package com.marp.apirickymorty.model

/**
 * Representa un personaje de la serie "Rick and Morty".
 *
 * @property id El ID del personaje.
 * @property name El nombre del personaje.
 * @property status El estado actual del personaje.
 * @property species La especie del personaje.
 * @property gender El genero del personaje.
 * @property origin Un objeto [Origin] que representa el origen del personaje.
 * @property image La URL de la imagen del personaje.
 *
 * @author Miguel Angel Ramirez Perez
 */

data class Personaje(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Origin,
    val image: String
) {
    /**
     * Representa el origen de un personaje.
     *
     * @property name El nombre del lugar de origen del personaje.
     */

    data class Origin(val name: String)
}