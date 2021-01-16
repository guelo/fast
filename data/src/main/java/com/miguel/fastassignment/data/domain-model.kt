package com.miguel.fastassignment.data

import retrofit2.Response
import java.net.URL

data class Movie(
    val title: String,
    val year: String,
    val type: Type,
    val imageUrl: URL?
) {
    enum class Type(private val jsonName: String) {
        MOVIE("movie"),
        SERIES("series"),
        EPISODE("episode"),
        GAME("game");

        companion object {
            fun fromJsonName(value: String): Type? = values().find { it.jsonName == value }
        }

    }
}

class HttpException(override val message: String) : RuntimeException() {
    constructor(response: Response<*>) : this("${response.code()}  + ${response.message()}")
}
