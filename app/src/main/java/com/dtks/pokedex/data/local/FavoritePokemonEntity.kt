package com.dtks.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_pokemon"
)
data class FavoritePokemonEntity(
    @PrimaryKey val pokemonId: Int,
    val isFavorite: Boolean = false,
)