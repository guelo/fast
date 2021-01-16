package com.miguel.data

import com.miguel.fastassignment.data.network.parse
import junit.framework.TestCase.assertEquals
import org.json.JSONObject
import org.junit.Test

class JsonSerializationTess {
    @Test
    fun validateJson() {

        val response = parse(JSONObject(SAMPLE_RESPONSE))

        assertEquals("The Cake Eaters", response[4].title)
    }
}

const val SAMPLE_RESPONSE = """
{
  "Search": [
    {
      "Title": "Layer Cake",
      "Year": "2004",
      "imdbID": "tt0375912",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BMTI5MTE1OTAzOV5BMl5BanBnXkFtZTcwNDc2OTgyMQ@@._V1_SX300.jpg"
    },
    {
      "Title": "Cake",
      "Year": "2014",
      "imdbID": "tt3442006",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BMmE2ZTQ5NTUtMzM2NS00YTIxLWEyMDQtMjBiMGNmODgwN2U5XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
    },
    {
      "Title": "Snow Cake",
      "Year": "2006",
      "imdbID": "tt0448124",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BMTU0MDk5OTQyM15BMl5BanBnXkFtZTYwMDY3ODc3._V1_SX300.jpg"
    },
    {
      "Title": "Patti Cake${'$'}",
      "Year": "2017",
      "imdbID": "tt6288250",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BOTZiNGIxNzgtMGUyMy00Y2IwLWI0Y2QtN2FhNWNhNjcyODM5XkEyXkFqcGdeQXVyMDc2NTEzMw@@._V1_SX300.jpg"
    },
    {
      "Title": "The Cake Eaters",
      "Year": "2007",
      "imdbID": "tt0418586",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BYzZkNTQ0YmItMzQ3OS00NWRjLTk1NTMtODE5YjYwNzFmNmI4XkEyXkFqcGdeQXVyMTMxMTY0OTQ@._V1_SX300.jpg"
    },
    {
      "Title": "Cake",
      "Year": "2005",
      "imdbID": "tt0413879",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BMTI1MDMxMTc0M15BMl5BanBnXkFtZTcwMDgyNjEzMQ@@._V1_SX300.jpg"
    },
    {
      "Title": "Cake Boss",
      "Year": "2009–",
      "imdbID": "tt1444382",
      "Type": "series",
      "Poster": "https://m.media-amazon.com/images/M/MV5BMTY0NDIwMjI1OV5BMl5BanBnXkFtZTgwNzg5MDgwMzE@._V1_SX300.jpg"
    },
    {
      "Title": "The Cake General",
      "Year": "2018",
      "imdbID": "tt6889032",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BNDgwODZmZDYtMDYzZC00YWNkLWFjMzYtNmE4MmMyZDJkOTA1XkEyXkFqcGdeQXVyMjQ3NzUxOTM@._V1_SX300.jpg"
    },
    {
      "Title": "Human Cake",
      "Year": "2016",
      "imdbID": "tt5953378",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BN2I1YzJlODAtNDE2OC00ZGI5LWI0MjMtMTEzZGY5NmY2M2E4XkEyXkFqcGdeQXVyMTQyNTU3NTg@._V1_SX300.jpg"
    },
    {
      "Title": "Cake",
      "Year": "2018",
      "imdbID": "tt7715988",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BYTY4MTNlZjktNDg2ZS00MmVmLWJhODgtNmMyZGY5ZDcyNTM4XkEyXkFqcGdeQXVyNTM3MzQ3NzE@._V1_SX300.jpg"
    }
  ],
  "totalResults": "407",
  "Response": "True"
}
"""