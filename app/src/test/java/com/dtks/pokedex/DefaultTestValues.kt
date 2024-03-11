package com.dtks.pokedex

import com.dtks.pokedex.data.apimodel.Artwork
import com.dtks.pokedex.data.apimodel.ExtraPictures
import com.dtks.pokedex.data.apimodel.Pokemon
import com.dtks.pokedex.data.apimodel.PokemonDetailsResponse
import com.dtks.pokedex.data.apimodel.PokemonSprites
import com.dtks.pokedex.data.apimodel.PokemonsResponse
import com.dtks.pokedex.data.local.PokemonEntity


val defaultPokemonEntity = PokemonEntity(1, "Zagzag")
val pokemonEntityWithPictureUrl = defaultPokemonEntity.copy(pictureUrl = "pic://")
val defaultRemoteResponse =
    PokemonsResponse(2, listOf(Pokemon(name = "Pika", url = "http://aa.ss")))
val defaultRemoteDetailsResponse =
    PokemonDetailsResponse(
        id = 15,
        name = "Koffin",
        height = 41,
        weight = 21,
        abilities = emptyList(),
        types = emptyList(),
        stats = emptyList(),
        sprites = PokemonSprites(
            pictureUrl = "url",
            other = ExtraPictures(Artwork("bigurl://"))
        )
    )