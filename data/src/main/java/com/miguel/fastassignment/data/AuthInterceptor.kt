package com.miguel.fastassignment.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor(private val KEY: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder().addQueryParameter("apikey", KEY).build()
        val newRequest = request.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }
}