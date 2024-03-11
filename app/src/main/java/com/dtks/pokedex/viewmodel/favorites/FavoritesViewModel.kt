package com.dtks.pokedex.viewmodel.favorites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.pokedex.data.local.FavoritePokemonEntity
import com.dtks.pokedex.data.local.PokemonEntity
import com.dtks.pokedex.data.repository.PokemonRepository
import com.dtks.pokedex.di.DefaultDispatcher
import com.dtks.pokedex.ui.navigation.PokedexDestinationsArgs
import com.dtks.pokedex.ui.overview.PokemonGridItem
import com.dtks.quickmuseum.utils.AsyncResource
import com.dtks.quickmuseum.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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