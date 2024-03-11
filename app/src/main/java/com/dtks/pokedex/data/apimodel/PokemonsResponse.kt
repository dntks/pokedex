package com.dtks.pokedex.data.apimodel

data class PokemonsResponse(
    val count: Int,
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String,
    val url: String
){
    fun getId(): Int?{
        return url.split('/').findLast { it.isNotEmpty() }?.toIntOrNull()
    }
}
