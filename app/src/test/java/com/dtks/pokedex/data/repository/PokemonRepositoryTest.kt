package com.dtks.pokedex.data.repository

import androidx.work.impl.utils.isDefaultProcess
import com.dtks.pokedex.data.apimodel.Artwork
import com.dtks.pokedex.data.apimodel.ExtraPictures
import com.dtks.pokedex.data.apimodel.Pokemon
import com.dtks.pokedex.data.apimodel.PokemonDetailsResponse
import com.dtks.pokedex.data.apimodel.PokemonSprites
import com.dtks.pokedex.data.apimodel.PokemonsResponse
import com.dtks.pokedex.data.local.FavoritePokemonDao
import com.dtks.pokedex.data.local.FavoritePokemonEntity
import com.dtks.pokedex.data.local.PokemonDao
import com.dtks.pokedex.data.local.PokemonEntity
import com.dtks.pokedex.defaultPokemonEntity
import com.dtks.pokedex.defaultRemoteDetailsResponse
import com.dtks.pokedex.defaultRemoteResponse
import com.dtks.pokedex.pokemonEntityWithPictureUrl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PokemonRepositoryTest {

    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)
    val remoteDataSourceMock = mockk<RemoteDataSource>()
    val localPokemonDataSource = mockk<PokemonDao>()
    val favoritesDataSource = mockk<FavoritePokemonDao>()
    val repository = PokemonRepository(
        remoteDataSourceMock,
        localPokemonDataSource,
        favoritesDataSource
    )


    @Before
    fun setup() {
        coEvery { remoteDataSourceMock.getPokemons() } returns defaultRemoteResponse
        coEvery { remoteDataSourceMock.pokemonDetails(any()) } returns defaultRemoteDetailsResponse
        coEvery { localPokemonDataSource.getById(any()) } returns defaultPokemonEntity
        coEvery { localPokemonDataSource.getAll() } returns listOf(defaultPokemonEntity)
        coEvery { localPokemonDataSource.upsert(any()) } returns Unit
        coEvery { favoritesDataSource.update(any()) } returns Unit
        coEvery { favoritesDataSource.getFavoriteById(any()) } returns FavoritePokemonEntity(
            pokemonId = 134,
            false
        )
    }

    @Test
    fun favoritePokemonCallsCorrectDataSources() = testScope.runTest {
        val isFavorite = repository.favoritePokemon(5)

        coVerify { favoritesDataSource.getFavoriteById(5) }
        coVerify { favoritesDataSource.update(FavoritePokemonEntity(5, true)) }

        Assert.assertTrue(isFavorite)
    }

    @Test
    fun getPokemonDetailsWhenPictureUrlExists() = testScope.runTest {
        coEvery { localPokemonDataSource.getById(any()) } returns pokemonEntityWithPictureUrl

        val expected = pokemonEntityWithPictureUrl

        val pokemonDetails = repository.getPokemonDetails(5)

        coVerify(exactly = 1) { localPokemonDataSource.getById(5) }
        coVerify(exactly = 0) { remoteDataSourceMock.pokemonDetails(5) }
        coVerify(exactly = 0) { localPokemonDataSource.upsert(expected) }

        Assert.assertEquals(pokemonDetails, expected)
    }

    @Test
    fun getPokemonDetailsReturnsExpectedValue() = testScope.runTest {
        val expected = PokemonEntity(defaultRemoteDetailsResponse)
        val pokemonDetails = repository.getPokemonDetails(5)

        coVerify { localPokemonDataSource.getById(5) }
        coVerify { remoteDataSourceMock.pokemonDetails(5) }
        coVerify { localPokemonDataSource.upsert(expected) }

        Assert.assertEquals(pokemonDetails, expected)
    }

    @Test
    fun getPokemonsDoesOnlyCallLocalIfValuesArePresent() = testScope.runTest {
        val expected = listOf(defaultPokemonEntity)

        val pokemonDetails = repository.getPokemons()

        coVerify(exactly = 0) { remoteDataSourceMock.getPokemons() }
        coVerify(exactly = 0) { localPokemonDataSource.insertIfNotPresent(any()) }

        Assert.assertEquals(pokemonDetails, expected)
    }

    @Test
    fun getPokemonsCallsMethodsIfValuesAreNotPresent() = testScope.runTest {
        val expected = listOf(defaultPokemonEntity)
        coEvery { localPokemonDataSource.getAll() } returns emptyList()
        coEvery { localPokemonDataSource.insertIfNotPresent(any()) } returns Unit

        val pokemonDetails = repository.getPokemons()

        coVerify { localPokemonDataSource.getAll() }
        coVerify { localPokemonDataSource.insertIfNotPresent(any()) }
        coVerify { remoteDataSourceMock.getPokemons() }
    }
}