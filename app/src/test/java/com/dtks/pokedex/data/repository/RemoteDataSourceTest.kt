package com.dtks.pokedex.data.repository

import com.dtks.pokedex.data.api.PokeApi
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class RemoteDataSourceTest{

    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)
    val pokeApiMock: PokeApi = mockk()
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup(){
        remoteDataSource = RemoteDataSource(pokeApiMock)
    }

    @Test
    fun testgetPokemonsMethodIsCalled() = testScope.runTest {
        remoteDataSource.getPokemons()

        coVerify { pokeApiMock.getPokemons(any()) }
    }

    @Test
    fun testMethodIsCalled() = testScope.runTest {
        remoteDataSource.pokemonDetails(42)

        coVerify { pokeApiMock.getPokemonDetails(42) }
    }
}