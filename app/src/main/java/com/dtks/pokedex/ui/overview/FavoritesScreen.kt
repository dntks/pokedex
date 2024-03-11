package com.dtks.pokedex.ui.overview

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dtks.pokedex.R
import com.dtks.pokedex.ui.theme.Typography
import com.dtks.pokedex.viewmodel.favorites.FavoritesViewModel
import com.dtks.pokedex.viewmodel.overview.OverviewViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onPokemonDetailsClick: (PokemonGridItem) -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    overviewViewModel: OverviewViewModel = hiltViewModel(),
) {
    val favorites by favoritesViewModel.favoriteFlow.collectAsStateWithLifecycle()
    val allPokemon by overviewViewModel.allPokemon.collectAsStateWithLifecycle()

    val title = R.string.my_favorites
    PokemonGridWithTitle(
        modifier = modifier,
        title = title,
        searchBarContent = { },
        gridContent = {
            val favoritePokemons = allPokemon.filter { pokemonEntry ->
                favorites.map { it.pokemonId }.contains(pokemonEntry.key)
            }
            if (favoritePokemons.isNotEmpty()) {
                PokemonGrid(favoritePokemons, overviewViewModel, onPokemonDetailsClick)
            } else {
                Text(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.generic_padding)),
                    text = stringResource(id = R.string.no_favorites),
                    style = Typography.bodyMedium
                )
            }
        },
    )
}