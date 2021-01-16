package com.miguel.fastassignment.data.network

import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OMDBApi {
    @GET("/")
    suspend fun search(
        @Query("s") term: String,
        @Query("y") year: String? = null,
        @Query("page") page: String? = null
    ): Response<String>
}

internal data class OMDBJsonResponse(
    val Search: List<Item>,
    val totalResults: Int,
    val Response: String,
) {
    data class Item(
        val Title: String,// "Cake Boss",
        val Year: String,// "2009–",
        val imdbID: String,// "tt1444382",
        val Type: String,// "series",
        val Poster: String,// "https://m.media-amazon.com/images/M/MV5BMTY0NDIwMjI1OV5BMl5BanBnXkFtZTgwNzg5MDgwMzE@._V1_SX300.jpg"
    )
}