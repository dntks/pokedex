package com.dtks.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class, FavoritePokemonEntity::class], version = 1, exportSchema = false)
abstract class PokeDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun favoritePokemonDao(): FavoritePokemonDao
}