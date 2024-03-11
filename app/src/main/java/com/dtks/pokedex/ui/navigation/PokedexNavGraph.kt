package com.dtks.pokedex.ui.navigation

import android.window.SplashScreen
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dtks.pokedex.R
import com.dtks.pokedex.ui.details.DetailsScreen
import com.dtks.pokedex.ui.navigation.PokedexDestinationsArgs.USER_MESSAGE_ARG
import com.dtks.pokedex.ui.overview.FavoritesScreen
import com.dtks.pokedex.ui.overview.OverviewScreen
import com.dtks.pokedex.ui.overview.PokemonSplashScreen
import com.dtks.pokedex.ui.theme.IconTint
import com.dtks.pokedex.ui.theme.IconTintSelected
import com.dtks.pokedex.ui.theme.PrimaryColor
import com.dtks.pokedex.ui.theme.SearchFieldGray
import com.dtks.pokedex.ui.theme.TextPrimaryColor
import com.dtks.pokedex.viewmodel.favorites.FavoritesViewModel
import com.dtks.pokedex.viewmodel.overview.OverviewViewModel

sealed class TabBarItem(
    val route: String,
    @StringRes val titleResource: Int,
    @DrawableRes val icon: Int
) {
    data object Pokemons :
        TabBarItem(PokedexScreens.OVERVIEW_SCREEN, R.string.pokemons, R.drawable.ic_pokeball)

    data object Favorites :
        TabBarItem(PokedexScreens.FAVORITES_SCREEN, R.string.favorites, R.drawable.ic_favorite)
}

@Composable
fun PokedexNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = PokedexDestinations.OVERVIEW_ROUTE,
    navActions: PokedexNavigationActions = remember(navController) {
        PokedexNavigationActions(navController)
    },
    overviewViewModel: OverviewViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var splashShowing by remember { mutableStateOf(true) }
        if (splashShowing) {
            PokemonSplashScreen(onSplashEnd = {
                splashShowing = false
            })
        } else {


            Scaffold(
                bottomBar = {
                    TabView(
                        listOf(
                            TabBarItem.Pokemons,
                            TabBarItem.Favorites,
                        ),
                        navController
                    )
                }
            ) { paddingValues ->

                paddingValues.toString()
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = modifier
                ) {
                    composable(
                        route = PokedexDestinations.OVERVIEW_ROUTE,
                        arguments = listOf(
                            navArgument(USER_MESSAGE_ARG) {
                                type = NavType.IntType; defaultValue = 0
                            }
                        )
                    ) {
                        OverviewScreen(
                            onPokemonDetailsClick = { pokemon ->
                                navActions.navigateToPokemonDetail(
                                    pokemon
                                )
                            },
                            viewModel = overviewViewModel
                        )
                    }
                    composable(
                        route = PokedexDestinations.FAVORITES_ROUTE,
                    ) {
                        FavoritesScreen(
                            onPokemonDetailsClick = { pokemon ->
                                navActions.navigateToPokemonDetail(
                                    pokemon
                                )
                            },
                            overviewViewModel = overviewViewModel,
                            favoritesViewModel = favoritesViewModel
                        )
                    }
                    composable(route = PokedexDestinations.DETAILS_ROUTE) {
                        DetailsScreen(
                            onBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabView(
    tabBarItems: List<TabBarItem>,
    navController: NavController,
) {
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            val title = stringResource(id = tabBarItem.titleResource)
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.route)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        icon = tabBarItem.icon,
                        title = title,
                    )
                },
                label = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.displayMedium
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = IconTintSelected,
                    selectedTextColor = TextPrimaryColor,
                    selectedIndicatorColor = PrimaryColor,
                    unselectedIconColor = IconTint,
                    unselectedTextColor = TextPrimaryColor,
                    disabledIconColor = SearchFieldGray,
                    disabledTextColor = TextPrimaryColor
                )
            )
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    @DrawableRes icon: Int,
    title: String,
) {
    Icon(
        modifier = Modifier
            .wrapContentSize(),
        painter = painterResource(id = icon),
        tint = (if (isSelected) IconTintSelected else IconTint),
        contentDescription = title
    )
}

@Preview
@Composable
fun tabBarIconSelectedPreview() {
    TabBarIconView(isSelected = true, icon = R.drawable.ic_pokeball, title = "Pokemon")
}

@Preview
@Composable
fun tabBarIconUnselectedPreview() {
    TabBarIconView(isSelected = false, icon = R.drawable.ic_pokeball, title = "Pokemon")
}