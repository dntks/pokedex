package com.dtks.pokedex.data.apimodel

import com.google.gson.annotations.SerializedName

data class PokemonSprites(
    @SerializedName("front_default")
    val pictureUrl: String,
    val other: ExtraPictures
)
data class ExtraPictures(
    @SerializedName("official-artwork")
    val artwork: Artwork
)
data class Artwork(
    @SerializedName("front_default")
    val pictureBigUrl: String
)
