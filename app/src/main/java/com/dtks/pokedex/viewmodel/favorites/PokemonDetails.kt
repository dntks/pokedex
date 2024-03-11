package com.dtks.pokedex.viewmodel.favorites

import com.dtks.pokedex.data.local.PokemonEntity
import com.dtks.pokedex.data.local.PokemonStat
import com.dtks.pokedex.ui.details.PokemonType
import com.dtks.pokedex.utils.fromJson
import com.google.gson.Gson

data class PokemonDetails(
    val id: Int,
    val name: String,
    val pictureUrl: String?,
    val pictureBigUrl: String?,
    val isFavorite: Boolean,
    val height: Int? = null,
    val weight: Int? = null,
    val abilities: List<String> = emptyList(),
    val stats: List<PokemonStat> = emptyList(),
    val types: List<PokemonType> = emptyList()
) {
    constructor(pokemonEntity: PokemonEntity, isFavorite: Boolean) : this(
        id = pokemonEntity.id,
        name = pokemonEntity.name,
        pictureUrl = pokemonEntity.pictureUrl,
        pictureBigUrl = pokemonEntity.pictureBigUrl,
        isFavorite = isFavorite,
        height = pokemonEntity.height,
        weight = pokemonEntity.weight,
        abilities = Gson().fromJson(pokemonEntity.abilities ?: ""),
        stats = Gson().fromJson(pokemonEntity.stats ?: ""),
        types = Gson().fromJson(pokemonEntity.types ?: ""),
    )
}
