package com.dtks.pokedex.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.pokedex.data.local.FavoritePokemonEntity
import com.dtks.pokedex.data.repository.PokemonRepository
import com.dtks.pokedex.di.DefaultDispatcher
import com.dtks.pokedex.ui.navigation.PokedexDestinationsArgs
import com.dtks.pokedex.utils.fromJson
import com.dtks.pokedex.viewmodel.favorites.PokemonDetails
import com.dtks.quickmuseum.utils.WhileUiSubscribed
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val pokemonId: String = savedStateHandle[PokedexDestinationsArgs.POKEMON_ID_ARG]!!
    private val favoritePokemonsFlow = pokemonRepository.favoritePokemonsFlow()
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = emptyList()
        )
    private val _detailsFlow: MutableStateFlow<PokemonDetails?> = MutableStateFlow(null)
    val detailsFlow = _detailsFlow.combine(pokemonRepository.favoritePokemonsFlow()){
        details, favorites ->
        details?.let { pokeDetails ->
            pokeDetails.copy(
                isFavorite = favorites.filter { it.isFavorite }.map { it.pokemonId }
                    .contains(details.id)
            )
        }
    }.stateIn(viewModelScope, WhileUiSubscribed, null)

    init {
        pokemonId.toIntOrNull()?.let { id ->
            viewModelScope.launch(dispatcher) {
                val pokemonDetails = pokemonRepository.getPokemonDetails(id)
                val favoritePokemons = favoritePokemonsFlow.value
                _detailsFlow.value = PokemonDetails(
                    pokemonDetails,
                    favoritePokemons.filter { it.isFavorite }.map { it.pokemonId }
                        .contains(pokemonDetails.id))
            }
        }
    }

    fun pokemonFavoriteClicked(pokemonId: Int) {
        viewModelScope.launch(dispatcher) {
            val isFavorite = pokemonRepository.favoritePokemon(pokemonId)
            _detailsFlow.value = _detailsFlow.value?.copy(isFavorite = isFavorite)
        }
    }
}