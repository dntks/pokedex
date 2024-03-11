package com.dtks.pokedex.ui.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import com.dtks.pokedex.R
import com.dtks.pokedex.viewmodel.overview.OverviewViewModel

@Composable
fun PokemonGrid(
    pokemons: Map<Int, PokemonGridItem>,
    viewModel: OverviewViewModel,
    onPokemonDetailsClick: (PokemonGridItem) -> Unit
) {
    val genericPadding = dimensionResource(id = R.dimen.generic_padding)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = genericPadding,
            end = genericPadding,
            top = genericPadding,
            bottom = dimensionResource(id = R.dimen.extraBottom),
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.grid_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.grid_padding))
    ) {
        items(pokemons.values.toList(), key = {
            it.id
        }) { pokemonGridItem ->

            PokemonGridCard(pokemonGridItem, onPokemonDetailsClick)
            if (pokemonGridItem.pictureUrl == null) {
                viewModel.getPokemonDetails(pokemonGridItem.id)
            }
        }
    }
}
