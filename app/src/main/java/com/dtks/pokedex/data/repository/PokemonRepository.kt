package com.dtks.pokedex.data.repository

import com.dtks.pokedex.data.local.FavoritePokemonDao
import com.dtks.pokedex.data.local.FavoritePokemonEntity
import com.dtks.pokedex.data.local.PokemonDao
import com.dtks.pokedex.data.local.PokemonEntity
import com.dtks.pokedex.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localPokemonDataSource: PokemonDao,
    private val localFavoritePokemonDataSource: FavoritePokemonDao,
) {
    suspend fun getPokemonDetails(id: Int): PokemonEntity {
        val localPokemon = localPokemonDataSource.getById(id)
        return if (localPokemon?.pictureUrl == null) {
            val pokemonDetails = remoteDataSource.pokemonDetails(id)
            val pokemonEntity = PokemonEntity(pokemonDetails)
            localPokemonDataSource.upsert(pokemonEntity)
            pokemonEntity
        } else {
            localPokemon
        }
    }

    suspend fun getPokemons(): List<PokemonEntity> {
        val localPokemons = localPokemonDataSource.getAll()
        return localPokemons.ifEmpty {
            val pokemons = remoteDataSource.getPokemons().results.mapNotNull { pokemon ->
                pokemon.getId()?.let { id ->
                    PokemonEntity(id, pokemon.name)
                }
            }
            localPokemonDataSource.insertIfNotPresent(pokemons)
            pokemons
        }
    }

    suspend fun favoritePokemon(id: Int): Boolean {
        val favoriteById = localFavoritePokemonDataSource.getFavoriteById(id)
        val newValue = favoriteById?.isFavorite?.not() ?: true
        localFavoritePokemonDataSource.update(FavoritePokemonEntity(id, newValue))
        return newValue
    }

    fun favoritePokemonsFlow(): Flow<List<FavoritePokemonEntity>> {
        return localFavoritePokemonDataSource.favoritesFlow()
    }
}