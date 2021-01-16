package com.miguel.fastassignment.data.di

import com.miguel.fastassignment.data.AuthInterceptor
import com.miguel.fastassignment.data.network.OMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Provides
    fun authKey() = "6ba646fa"

    @Provides
    @Singleton
    internal fun okHttp(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

    internal var BASE_URL = "http://www.omdbapi.com/".toHttpUrl()

    @Provides
    @Singleton
    internal fun retrofit(
        okHttp: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun omdb(retrofit: Retrofit): OMDBApi = retrofit.create(OMDBApi::class.java)


}