package com.dtks.pokedex.di

import android.content.Context
import androidx.room.Room
import com.dtks.pokedex.data.local.FavoritePokemonDao
import com.dtks.pokedex.data.local.PokeDatabase
import com.dtks.pokedex.data.local.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): PokeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PokeDatabase::class.java,
            "Pokemon.db"
        ).build()
    }

    @Provides
    fun providePokemonDao(database: PokeDatabase): PokemonDao = database.pokemonDao()

    @Provides
    fun provideFavPokemonDao(database: PokeDatabase): FavoritePokemonDao = database.favoritePokemonDao()
}
