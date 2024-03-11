package com.dtks.pokedex.data.repository

import com.dtks.pokedex.data.api.PokeApi
import com.dtks.pokedex.data.apimodel.PokemonDetailsResponse
import com.dtks.pokedex.data.apimodel.PokemonsResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val pokeApi: PokeApi
) {
    suspend fun pokemonDetails(id: Int): PokemonDetailsResponse {
        return pokeApi.getPokemonDetails(id)
    }
    suspend fun getPokemons(): PokemonsResponse {
        return pokeApi.getPokemons()
    }
}