package com.miguel.fastassignment.data

import com.miguel.fastassignment.data.network.Network
import com.miguel.fastassignment.data.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject internal constructor(private val network: Network) {

    suspend fun search(term: String): NetworkResponse<out List<Movie>> = coroutineScope {
        withContext(Dispatchers.IO) {
            network.search(term)
        }
    }
}