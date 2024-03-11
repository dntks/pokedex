package com.dtks.pokedex.ui.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dtks.pokedex.R
import com.dtks.pokedex.viewmodel.overview.OverviewViewModel
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import com.dtks.pokedex.ui.theme.SearchFieldGray
import com.dtks.pokedex.ui.theme.Typography

@Composable
fun PokemonSearchTopBar(
    viewModel: OverviewViewModel
) {

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by mutableStateOf(searchText.isNotEmpty())

    Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.generic_padding))) {
        Card(

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.card_padding))
            ) {
                if (isSearching) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                viewModel.onSearchTextChange("")
                            },
                        contentDescription = stringResource(id = R.string.back)
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
                TextField(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    value = searchText,
                    onValueChange = {
                        viewModel.onSearchTextChange(it)
                    },

                    textStyle = Typography.labelMedium,
                    colors = TextFieldDefaults.colors(

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,

                        //setting the text field background when it is unfocused or initial state
                        unfocusedContainerColor = Color.Transparent,

                        //setting the text field background when it is disabled
                        disabledContainerColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            style = Typography.labelMedium,
                            color = SearchFieldGray,
                            text = stringResource(id = R.string.search_for_pokemon))
                    }
                )
            }
        }
    }
}