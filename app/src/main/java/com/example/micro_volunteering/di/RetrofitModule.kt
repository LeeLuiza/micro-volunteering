package com.example.micro_volunteering.di

import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.data.remote.interceptors.AuthInterceptor
import com.example.micro_volunteering.data.remote.interceptors.ErrorInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideOkHttp(tokenManager: TokenManager) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ErrorInterceptor())
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}