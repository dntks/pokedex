package com.dtks.pokedex.ui.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.dtks.pokedex.R

@Composable
fun LoadingContent(
    loading: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
//    val networkConnectivity by connectivityState()
    if (empty && loading) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(dimensionResource(id = R.dimen.progress_indicator_size)),
                color = MaterialTheme.colorScheme.secondary,
            )
        }
//    } else if (networkConnectivity == ConnectionState.Unavailable) {
//        noInternetContent()
    }else if (empty) {
        emptyContent()
    } else {
        content()
    }

}

@Preview
@Composable
fun PreviewLoadingContentLoadingTrue() {
    LoadingContent(
        loading = true,
        empty = true,
        emptyContent = {Text("empty")},
        content =  {Text("real content")}
    )
}
@Preview
@Composable
fun PreviewLoadingContentEmptyTrue() {
    LoadingContent(
        loading = false,
        empty = true,
        emptyContent = {Text("empty")},
        content =  {Text("real content")}
    )
}

@Preview
@Composable
fun PreviewLoadingContentWithContent() {
    LoadingContent(
        loading = false,
        empty = false,
        emptyContent = {Text("empty")},
        content =  {Text("real content")}
    )
}