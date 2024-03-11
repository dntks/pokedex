package com.dtks.pokedex.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun Int.toPaddedString() = toString().padStart(3, '0')