package com.dtks.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dtks.pokedex.data.apimodel.PokemonDetailsResponse
import com.dtks.pokedex.data.apimodel.PokemonStatResponse
import com.dtks.pokedex.data.apimodel.PokemonTypeResponse
import com.dtks.pokedex.ui.details.PokemonDetailsType
import com.dtks.pokedex.ui.details.PokemonType
import com.google.gson.Gson

@Entity(
    tableName = "pokemon"
)
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val pictureUrl: String? = null,
    val pictureBigUrl: String? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val abilities: String? = null,
    val stats: String? = null,
    val types: String? = null
) {

    constructor(pokemonResponse: PokemonDetailsResponse) : this(
        pokemonResponse.id,
        pokemonResponse.name,
        pokemonResponse.sprites.pictureUrl,
        pokemonResponse.sprites.other.artwork.pictureBigUrl,
        pokemonResponse.height,
        pokemonResponse.weight,
        Gson().toJson(pokemonResponse.abilities.mapNotNull { it.ability.name }),
        Gson().toJson(pokemonResponse.stats.mapNotNull {
            PokemonStat(
                value = it.baseStat,
                name = it.stat.name
            )
        }),
        Gson().toJson(pokemonResponse.types.mapNotNull {
            PokemonType(it.type.name, PokemonDetailsType.getByName(it.type.name))

        })

    )

    constructor(pokemonId: Int, pokemonName: String) : this(
        id = pokemonId,
        name = pokemonName
    )
}

data class PokemonStat(
    val value: Int,
    val name: String
)