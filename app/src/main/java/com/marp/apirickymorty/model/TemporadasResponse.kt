package com.marp.apirickymorty.model

/**
 * Representa una respuesta de la API que contiene informacion sobre las temporadas disponibles.
 *
 * @property info Un objeto [Info] que contiene informacion de resultados.
 * @property results Una lista de objetos [Temporadas] que representan las temporadas obtenidas de la API.
 *
 * @author Miguel Angel Ramirez Perez
 */

data class TemporadasResponse(
    val info: Info,
    val results: List<Temporadas>
)