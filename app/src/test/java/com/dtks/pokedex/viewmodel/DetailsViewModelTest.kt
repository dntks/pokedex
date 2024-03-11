package com.dtks.pokedex.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.dtks.pokedex.MainCoroutineRule
import com.dtks.pokedex.data.repository.PokemonRepository
import com.dtks.pokedex.defaultPokemonEntity
import com.dtks.pokedex.viewmodel.overview.OverviewViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailsViewModelTest{
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)
    private lateinit var detailsViewModel: DetailsViewModel
    val repositoryMock: PokemonRepository = mockk()
    val savedStateHandleMock: SavedStateHandle = mockk()

    @Before
    fun setup() {
        coEvery { repositoryMock.getPokemons() } returns listOf()
        coEvery { repositoryMock.getPokemonDetails(any()) } returns defaultPokemonEntity

    }

    @Test
    fun getPokemonsCallsRepository() = testScope.runTest {
        coVerify { repositoryMock.getPokemons() }

    }

}