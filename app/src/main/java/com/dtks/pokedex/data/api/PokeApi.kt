package com.dtks.pokedex.data.api

import com.dtks.pokedex.data.apimodel.PokemonDetailsResponse
import com.dtks.pokedex.data.apimodel.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id")
        id: Int = 1
    ): PokemonDetailsResponse

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit")
        limit: Int = 1500
    ): PokemonsResponse
}