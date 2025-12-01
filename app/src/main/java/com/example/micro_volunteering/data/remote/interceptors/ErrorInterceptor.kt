package com.example.micro_volunteering.data.remote.interceptors

import com.example.micro_volunteering.domain.event.NetworkErrorManager
import com.example.micro_volunteering.domain.model.AppError
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            val response = chain.proceed(request)

            if (!response.isSuccessful) {
                NetworkErrorManager.sendError(AppError.ServerError(response.code))
            }
            return response
        } catch (e: IOException) {
            NetworkErrorManager.sendError(AppError.NoInternet)
            throw e
        } catch (e: Exception) {
            NetworkErrorManager.sendError(AppError.Unknown)
            throw e
        }
    }
}