package com.dtks.pokedex.ui.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dtks.pokedex.R
import com.dtks.pokedex.ui.theme.CardBackground
import com.dtks.pokedex.ui.theme.PrimaryColor
import com.dtks.pokedex.ui.theme.Typography
import com.dtks.pokedex.utils.toPaddedString
import com.dtks.pokedex.viewmodel.DetailsViewModel
import com.dtks.pokedex.viewmodel.favorites.FavoritesViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonGridCard(
    pokemonGridItem: PokemonGridItem,
    onPokemonDetailsClick: (PokemonGridItem) -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {

    val favorites by favoritesViewModel.favoriteFlow.collectAsStateWithLifecycle()

    Card(

        colors = CardDefaults.cardColors(
            containerColor = CardBackground,
        ),
        modifier = Modifier
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.card_padding))
                    .clickable { onPokemonDetailsClick.invoke(pokemonGridItem) }
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                    GlideImage(
                        model = pokemonGridItem.pictureUrl,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .align(Alignment.Center),
                        contentDescription = stringResource(id = R.string.pokemon_image)
                    )

                    Box(
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(PrimaryColor)
                            .padding(dimensionResource(id = R.dimen.id_padding))
                    ) {
                        Text(
                            text = pokemonGridItem.id.toPaddedString(),
                            color = Color.White,
                            style = Typography.labelSmall
                        )
                    }
                }

            }
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(dimensionResource(id = R.dimen.card_padding))
            ) {
                Text(
                    text = pokemonGridItem.name.replaceFirstChar(Char::titlecase),
                    modifier = Modifier.weight(1f)
                )

                var expanded by remember { mutableStateOf(false) }

                if (expanded) {

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.open_pokemon)) },
                            onClick = { onPokemonDetailsClick(pokemonGridItem) }
                        )
                        var gridItem =
                            pokemonGridItem.copy(isFavorite = favorites.map { it.pokemonId }
                                .contains(pokemonGridItem.id))
                        DropdownMenuItem(
                            text = {
                                val text =
                                    if (gridItem.isFavorite) stringResource(id = R.string.remove_favorite)
                                    else stringResource(id = R.string.add_to_favorite)
                                Text(text)
                            },
                            onClick = {
                                favoritesViewModel.pokemonFavoriteClicked(pokemonGridItem.id)
                                expanded = false
                            }
                        )
                    }
                }
                Image(
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    },
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = stringResource(
                        id = R.string.details
                    )
                )
            }
        }
    }
}
