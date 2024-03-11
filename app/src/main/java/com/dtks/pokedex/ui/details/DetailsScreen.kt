package com.dtks.pokedex.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dtks.pokedex.R
import com.dtks.pokedex.data.local.PokemonStat
import com.dtks.pokedex.ui.loading.LoadingContent
import com.dtks.pokedex.ui.theme.Typography
import com.dtks.pokedex.ui.util.EmptyContent
import com.dtks.pokedex.utils.toPaddedString
import com.dtks.pokedex.viewmodel.DetailsViewModel
import com.dtks.pokedex.viewmodel.favorites.PokemonDetails

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val pokemonDetails by viewModel.detailsFlow.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize(),
        topBar = {
            PokemonDetailsTopBar(
                onBack = onBack,
                detailsState = pokemonDetails,
            )
        },
        floatingActionButton = {}
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LoadingContent(
                empty = pokemonDetails == null,
                loading = pokemonDetails == null,
                emptyContent = {
                    EmptyContent(R.string.no_content)
                }
            ) {
                pokemonDetails?.let {
                    PokemonDetailsComposable(it)
                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonDetailsComposable(pokemonDetails: PokemonDetails) {

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.generic_padding))
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = pokemonDetails.name.replaceFirstChar(Char::titlecase),
                style = MaterialTheme.typography.titleLarge

            )
            Text(
                text = pokemonDetails.id.toPaddedString(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        LazyRow(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.id_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.generic_padding))
        ) {
            items(pokemonDetails.types) {
                TypeTag(type = it)
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            GlideImage(
                model = pokemonDetails.pictureBigUrl,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(270.dp),
                contentDescription = stringResource(id = R.string.pokemon_image)
            )
        }
        TabScreen(pokemonDetails)
    }
}

@Composable
fun TabScreen(pokemonDetails: PokemonDetails) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf(
        stringResource(id = R.string.about),
        stringResource(id = R.string.stats),
        stringResource(id = R.string.evolution)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {

                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )

                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> About(pokemonDetails)
            1 -> Stats(pokemonDetails.stats)
            2 -> {
                // not finished this part
            }
        }
    }
}

@Composable
fun Stats(stats: List<PokemonStat>) {
    stats.forEach {
        StatValueRow(it.name.replaceFirstChar(Char::titlecase), it.value.toString())
    }
}

@Composable
private fun StatValueRow(title: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            modifier = Modifier.width(150.dp),
            text = title,
            style = MaterialTheme.typography.bodyMedium

        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium

        )
    }
}

@Composable
fun About(pokemonDetails: PokemonDetails) {
    StatValueRow(
        stringResource(id = R.string.name),
        pokemonDetails.name.replaceFirstChar(Char::titlecase)
    )
    StatValueRow(stringResource(id = R.string.id), pokemonDetails.id.toPaddedString())
    val weightInKg = pokemonDetails.weight?.div(10f) ?: 0f
    val heightInMeters = pokemonDetails.height?.div(10f) ?: 0f
    StatValueRow(stringResource(id = R.string.weight), "$weightInKg kg")
    StatValueRow(stringResource(id = R.string.height), "$heightInMeters m")
    StatValueRow(
        stringResource(id = R.string.types),
        pokemonDetails.types.joinToString(", ") { it.name })
    StatValueRow(
        stringResource(id = R.string.abilities),
        pokemonDetails.abilities.joinToString { it })

}

@Composable
fun TypeTag(type: PokemonType) {

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(type.type.backgroundColor)
            .padding(dimensionResource(id = R.dimen.id_padding))
    ) {
        Text(
            text = type.name.replaceFirstChar(Char::titlecase),
            color = type.type.textColor,
            style = Typography.labelMedium
        )
    }
}
