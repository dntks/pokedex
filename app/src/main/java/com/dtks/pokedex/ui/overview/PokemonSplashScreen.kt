package com.dtks.pokedex.ui.overview

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dtks.pokedex.R
import com.dtks.pokedex.utils.ConnectionState
import com.dtks.pokedex.utils.connectivityState
import com.dtks.pokedex.viewmodel.overview.OverviewViewModel
import kotlinx.coroutines.delay

@Composable
fun PokemonSplashScreen(
    onSplashEnd: () -> Unit,
    viewModel: OverviewViewModel = hiltViewModel(),
) {
    val pokemonsState by viewModel.filteredPokemons.collectAsStateWithLifecycle()

    val networkConnectivity by connectivityState()

    val snackbarHostState = remember { SnackbarHostState() }
    var timePassed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000)
        timePassed = true
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {}
    ) { paddingValues ->

        if (networkConnectivity == ConnectionState.Available) {
            viewModel.getPokemons()
            if (pokemonsState.pokemons.isEmpty()
                    .not() && timePassed && networkConnectivity == ConnectionState.Available
            ) {
                onSplashEnd()
            }
        } else {
            showError(R.string.no_internet_connection, snackbarHostState)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_pokemon_logo),
                contentDescription = stringResource(id = R.string.logo)
            )
        }
    }
}

@Composable
private fun showError(
    @StringRes userMessage: Int,
    snackbarHostState: SnackbarHostState
) {
    val snackbarText = stringResource(userMessage)
    LaunchedEffect(userMessage, snackbarText) {
        snackbarHostState.showSnackbar(snackbarText)
    }
}