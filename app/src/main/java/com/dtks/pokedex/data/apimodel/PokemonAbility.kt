package com.dtks.pokedex.data.apimodel

import com.google.gson.annotations.SerializedName

data class PokemonAbility(
    val ability: NamedResource,
    @SerializedName("is_hidden")
    val isHidden: Boolean
)