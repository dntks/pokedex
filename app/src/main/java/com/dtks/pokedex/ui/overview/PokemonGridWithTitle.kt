package com.dtks.pokedex.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.dtks.pokedex.R
import com.dtks.pokedex.ui.theme.Typography

@Composable
 fun PokemonGridWithTitle(
    modifier: Modifier,
    title: Int,
    searchBarContent: @Composable () -> Unit,
    gridContent: @Composable () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {}
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
                .background(MaterialTheme.colorScheme.surface)
        ) {

            searchBarContent()
            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.generic_padding)),
                text = stringResource(id = title),
                style = Typography.titleLarge
            )
            gridContent()
        }
    }
}