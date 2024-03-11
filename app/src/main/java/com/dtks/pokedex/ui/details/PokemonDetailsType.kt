package com.dtks.pokedex.ui.details

import androidx.compose.ui.graphics.Color
import com.dtks.pokedex.ui.theme.ElectricYellow
import com.dtks.pokedex.ui.theme.FireRed
import com.dtks.pokedex.ui.theme.GrassGreen
import com.dtks.pokedex.ui.theme.PrimaryColor
import com.dtks.pokedex.ui.theme.TextPrimaryColor

enum class PokemonDetailsType(
    val backgroundColor: Color,
    val textColor: Color,
){
    ELECTRIC(ElectricYellow, TextPrimaryColor),
    POISON(GrassGreen, TextPrimaryColor),
    FIRE(FireRed, Color.White),
    NORMAL(PrimaryColor, Color.White);

    companion object {
        fun getByName(name: String): PokemonDetailsType{
            return when(name){
                "fire" ->  FIRE
                "poison", "grass" -> POISON
                "electric" -> ELECTRIC
                else -> NORMAL
            }
        }
    }
}

data class PokemonType(
    val name: String,
    val type: PokemonDetailsType
)