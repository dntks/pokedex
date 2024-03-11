package com.dtks.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.dtks.pokedex.data.apimodel.Pokemon
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {


    @Query("SELECT * FROM pokemon ORDER BY id")
    fun observeAllOrderById(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE id=:id ")
    suspend fun getById(id: Int): PokemonEntity?

    @Query("SELECT * FROM pokemon ORDER BY id")
    suspend fun getAll(): List<PokemonEntity>

    @Upsert
    suspend fun upsert(pokemonEntity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfNotPresent(pokemons: List<PokemonEntity>)

}
