package com.dtks.pokedex.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {

    @Query("SELECT * FROM favorite_pokemon WHERE pokemonId=:id ")
    suspend  fun getFavoriteById(id: Int): FavoritePokemonEntity?

    @Query("SELECT * FROM favorite_pokemon WHERE isFavorite = 1 ORDER BY pokemonId")
    fun favoritesFlow(): Flow<List<FavoritePokemonEntity>>

    @Upsert
    suspend fun update(entity: FavoritePokemonEntity)
}