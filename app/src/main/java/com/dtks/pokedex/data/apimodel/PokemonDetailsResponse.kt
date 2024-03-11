package com.dtks.pokedex.data.apimodel

data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>,
    val sprites: PokemonSprites,
    val stats: List<PokemonStatResponse>,
    val types: List<PokemonTypeResponse>,
)