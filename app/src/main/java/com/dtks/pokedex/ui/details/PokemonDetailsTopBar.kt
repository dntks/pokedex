package com.dtks.pokedex.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtks.pokedex.R
import com.dtks.pokedex.viewmodel.DetailsViewModel
import com.dtks.pokedex.viewmodel.favorites.FavoritesViewModel
import com.dtks.pokedex.viewmodel.favorites.PokemonDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsTopBar(
    onBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel(),
    detailsState: PokemonDetails?
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.back))
            }
        },
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = {
                detailsState?.let {
                    viewModel.pokemonFavoriteClicked(it.id)
                }
            }
            ) {
                val iconResource =
                    if (detailsState?.isFavorite == true) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
                Image(painterResource(id = iconResource), stringResource(id = R.string.favorite_button))

            }
        }
    )
}