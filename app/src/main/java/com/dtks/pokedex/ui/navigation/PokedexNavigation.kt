package com.dtks.pokedex.ui.navigation

import androidx.navigation.NavHostController
import com.dtks.pokedex.ui.navigation.PokedexDestinationsArgs.POKEMON_ID_ARG
import com.dtks.pokedex.ui.navigation.PokedexDestinationsArgs.USER_MESSAGE_ARG
import com.dtks.pokedex.ui.navigation.PokedexScreens.DETAILS_SCREEN
import com.dtks.pokedex.ui.navigation.PokedexScreens.FAVORITES_SCREEN
import com.dtks.pokedex.ui.navigation.PokedexScreens.OVERVIEW_SCREEN
import com.dtks.pokedex.ui.overview.PokemonGridItem


object PokedexScreens {
    const val OVERVIEW_SCREEN = "pokemons_overview"
    const val DETAILS_SCREEN = "pokemon_details"
    const val FAVORITES_SCREEN = "pokemon_favorites"
}
object PokedexDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val POKEMON_ID_ARG = "pokemonId"
}
object PokedexDestinations {
    const val OVERVIEW_ROUTE = "$OVERVIEW_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val DETAILS_ROUTE = "$DETAILS_SCREEN/{$POKEMON_ID_ARG}"
    const val FAVORITES_ROUTE = FAVORITES_SCREEN
}

class PokedexNavigationActions(private val navController: NavHostController) {
    fun navigateToPokemonDetail(pokemon: PokemonGridItem) {
        navController.navigate("$DETAILS_SCREEN/${pokemon.id}")
    }
}