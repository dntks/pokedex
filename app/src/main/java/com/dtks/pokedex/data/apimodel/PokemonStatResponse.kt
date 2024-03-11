package com.dtks.pokedex.data.apimodel

import com.google.gson.annotations.SerializedName

data class PokemonStatResponse(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: NamedResource
)
