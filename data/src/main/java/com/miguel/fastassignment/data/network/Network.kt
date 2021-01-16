package com.miguel.fastassignment.data.network

import androidx.annotation.WorkerThread
import com.miguel.fastassignment.data.HttpException
import com.miguel.fastassignment.data.Movie
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

internal class Network @Inject constructor(private val omdbApi: OMDBApi) {
    @WorkerThread
    suspend fun search(term: String): NetworkResponse<out List<Movie>> {
        return try {
            val response = omdbApi.search(term)
            if (response.isSuccessful) {
                try {
                    val jsonObject = JSONObject(response.body()!!)
                    if (jsonObject.has("Search")) {
                        NetworkResponse.SUCCESS(parse(jsonObject))
                    } else {
                        NetworkResponse.API_ERROR(HttpException(jsonObject.optString("Error")))
                    }
                } catch (err: JSONException) {
                    err.printStackTrace()
                    NetworkResponse.API_ERROR(HttpException("bad json: ${response.body()}"))
                }

            } else {
                NetworkResponse.API_ERROR(HttpException(response))
            }
        } catch (e: IOException) {
            NetworkResponse.NETWORK_ERROR(e)
        }
    }
}

internal fun parse(json: JSONObject): List<Movie> {
    val searchJsonArray = json.getJSONArray("Search")
    return (0 until searchJsonArray.length()).map {
        val obj = searchJsonArray.getJSONObject(it)
        val url = try {
            URL(obj.getString("Poster"))
        } catch (url: MalformedURLException) {
            null
        }
        Movie(
            title = obj.getString("Title"),
            year = obj.getString("Year"),
            type = Movie.Type.fromJsonName(obj.getString("Type"))!!,
            imageUrl = url
        )
    }
}
