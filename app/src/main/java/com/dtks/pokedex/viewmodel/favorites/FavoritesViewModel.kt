package com.dtks.pokedex.viewmodel.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.pokedex.data.repository.PokemonRepository
import com.dtks.pokedex.di.DefaultDispatcher
import com.dtks.quickmuseum.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val pokemonRepository: PokemonRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val favoriteFlow = pokemonRepository.favoritePokemonsFlow().stateIn(viewModelScope, WhileUiSubscribed, emptyList())

    fun pokemonFavoriteClicked(pokemonId: Int) {
        viewModelScope.launch(dispatcher) {
            val isFavorite = pokemonRepository.favoritePokemon(pokemonId)
        }
    }
}