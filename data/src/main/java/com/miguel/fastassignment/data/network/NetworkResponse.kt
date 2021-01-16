package com.miguel.fastassignment.data.network

import com.miguel.fastassignment.data.HttpException
import java.io.IOException

sealed class NetworkResponse<T> {
    data class SUCCESS<T>(val results: T) : NetworkResponse<T>()
    data class API_ERROR(val exception: HttpException) : NetworkResponse<Nothing>()
    data class NETWORK_ERROR(val exception: IOException) : NetworkResponse<Nothing>()
}