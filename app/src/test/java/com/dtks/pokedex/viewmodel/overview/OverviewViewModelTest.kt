package com.dtks.pokedex.viewmodel.overview

import com.dtks.pokedex.MainCoroutineRule
import com.dtks.pokedex.data.repository.PokemonRepository
import com.dtks.pokedex.defaultPokemonEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class OverviewViewModelTest{
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)
    private lateinit var overviewViewModel: OverviewViewModel
    val repositoryMock: PokemonRepository = mockk()

    @Before
    fun setup() {
        coEvery { repositoryMock.getPokemons() } returns listOf()
        coEvery { repositoryMock.getPokemonDetails(any()) } returns defaultPokemonEntity

        overviewViewModel = OverviewViewModel(
            pokemonRepository = repositoryMock,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun getPokemonsCallsRepository() = testScope.runTest {
        val allPokemonState = overviewViewModel.allPokemon
        Assert.assertTrue(allPokemonState.first().isEmpty())
        overviewViewModel.getPokemons()
        coVerify { repositoryMock.getPokemons() }

    }

    @Test
    fun searchTextTest() = testScope.runTest {
        val searchText = overviewViewModel.searchText
        Assert.assertEquals("",searchText.first())
        overviewViewModel.onSearchTextChange("rt")
        coVerify { repositoryMock.getPokemons() }
        Assert.assertEquals("rt", overviewViewModel.searchText.first())

    }
}