package com.dtks.pokedex.ui.overview

import com.dtks.pokedex.data.local.PokemonEntity

data class PokemonGridItem(
    val id: Int,
    val name: String,
    val pictureUrl: String?,
    val isFavorite: Boolean = false,
) {
    constructor(entity: PokemonEntity) : this(
        entity.id,
        entity.name,
        entity.pictureUrl,
    )

}