package com.dtks.pokedex.viewmodel.overview

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.pokedex.data.repository.PokemonRepository
import com.dtks.pokedex.di.DefaultDispatcher
import com.dtks.pokedex.ui.overview.PokemonGridItem
import com.dtks.pokedex.utils.connectivityState
import com.dtks.quickmuseum.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _allPokemon: MutableStateFlow<Map<Int, PokemonGridItem>> =
        MutableStateFlow(emptyMap())
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    val allPokemon = _allPokemon.asStateFlow()
    val filteredPokemons: StateFlow<PokemonGridUiState> =
        searchText.combine(_allPokemon) { searchText, uiState ->
            val filteredValues =
                uiState.filterValues { it.name.lowercase().startsWith(searchText) }
            PokemonGridUiState(filteredValues)
        }.stateIn(viewModelScope, WhileUiSubscribed, PokemonGridUiState())

    fun getPokemonDetails(id: Int) {
        viewModelScope.launch(dispatcher) {
            val entity = pokemonRepository.getPokemonDetails(id)
            val pokemonGridItem = PokemonGridItem(entity)
            val pokemons = _allPokemon.value.toMutableMap()
            pokemons[pokemonGridItem.id] = pokemonGridItem
            _allPokemon.value = pokemons
        }
    }

    fun getPokemons() {
        viewModelScope.launch(dispatcher) {
            val entities = pokemonRepository.getPokemons()
            val pokemonGridItems = entities.mapNotNull { PokemonGridItem(it) }
            val pokemons = _allPokemon.value.toMutableMap()
            pokemonGridItems.forEach {
                pokemons.putIfAbsent(it.id, it)
            }
            _allPokemon.value = pokemons
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

}

data class PokemonGridUiState(
    val pokemons: Map<Int, PokemonGridItem> = mapOf()
)
