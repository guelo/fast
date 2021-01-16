package com.miguel.data

import com.miguel.fastassignment.data.Movie
import com.miguel.fastassignment.data.di.DataModule
import com.miguel.fastassignment.data.network.Network
import com.miguel.fastassignment.data.network.NetworkResponse
import dagger.Component
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Test
import javax.inject.Singleton


/**
 * This integration test uses Dagger and a MockWebserver to ensure that all the layers are wired
 * correctly and it all works
 */
class IntegrationTests {

    @Test
    fun testHappyPath() = runBlocking {
        val server = MockWebServer().apply {
            enqueue(MockResponse().setBody(SAMPLE_RESPONSE))
            start()
        }
        val network = network(server)

        val response = network.search("cake")

        // some spot checks
        assertTrue(response is NetworkResponse.SUCCESS<*>)
        val movies = (response as NetworkResponse.SUCCESS).results
        assertEquals(10, movies.size)
        assertEquals("Patti Cake\$", movies[3].title)
        assertEquals(Movie.Type.MOVIE, movies[1].type)
        assertEquals("2018", movies[9].year)
    }

    @Test
    fun testApiErrorWith200() = runBlocking {
        val server = MockWebServer().apply {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody("""{"Response":"False","Error":"Movie not found!"}""")
            )
            start()
        }
        val network = network(server)

        val response = network.search("cake")

        assertTrue(response is NetworkResponse.API_ERROR)
        assertEquals("Movie not found!", (response as NetworkResponse.API_ERROR).exception.message)
    }

    @Test
    fun testApiErrorWithErrorCode() = runBlocking {
        val server = MockWebServer().apply {
            enqueue(
                MockResponse()
                    .setResponseCode(500)
                    .setBody("server error")
            )
            start()
        }
        val network = network(server)


        val response = network.search("cake")

        assertTrue(response is NetworkResponse.API_ERROR)
    }

    @Test
    fun testBadNetwork() = runBlocking {
        val server = MockWebServer().apply {
            enqueue(
                MockResponse()
                    .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START) // simulate network problems
            )
            start()
        }
        val network = network(server)

        val response = network.search("cake")

        assertTrue(response is NetworkResponse.NETWORK_ERROR)
    }


    private fun network(server: MockWebServer): Network {
        DataModule.BASE_URL = server.url("")

        val dagger = DaggerTestComponent.create()
        return dagger.network()
    }


}

@Component(modules = [DataModule::class])
@Singleton
internal interface TestComponent {
    fun network(): Network
}
