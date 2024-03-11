package com.dtks.pokedex.ui.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dtks.pokedex.R
import com.dtks.pokedex.viewmodel.overview.OverviewViewModel

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    onPokemonDetailsClick: (PokemonGridItem) -> Unit,
    viewModel: OverviewViewModel = hiltViewModel(),
) {
    val pokemonsState by viewModel.filteredPokemons.collectAsStateWithLifecycle()

    val title = R.string.all_pokemon
    PokemonGridWithTitle(
        modifier = modifier,
        title = title,
        searchBarContent = { PokemonSearchTopBar(viewModel) },
        gridContent = {
            if (pokemonsState.pokemons.isNotEmpty()) {
                PokemonGrid(pokemonsState.pokemons, viewModel, onPokemonDetailsClick)
            }
        },
    )
}