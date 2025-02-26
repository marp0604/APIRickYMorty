package com.marp.apirickymorty.model

/**
 * Representa informacion de paginacion y conteo de resultados obtenidos de la API.
 *
 * @property count El numero total de elementos disponibles en la API.
 * @property pages El numero total de páginas de resultados disponibles.
 * @property next La URL de la siguiente pagina de resultados.
 * @property prev La URL de la pagina anterior de resultados.
 *
 *  @author Miguel Angel Ramirez Perez
 */

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
