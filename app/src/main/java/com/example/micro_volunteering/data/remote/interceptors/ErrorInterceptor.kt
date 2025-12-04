package com.example.micro_volunteering.data.remote.interceptors

import com.example.micro_volunteering.data.constants.AuthConstants.MESSAGE
import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.domain.event.NetworkErrorManager
import com.example.micro_volunteering.domain.model.AppError
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ErrorInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            val response = chain.proceed(request)
            val code = response.code

            when (code) {
                in 200..299 -> {
                    return response
                }
                401 -> {
                    tokenManager.deleteToken()
                    NetworkErrorManager.sendError(AppError.Unauthorized)
                }
                in 400..499 -> {
                    val message = getErrorMessageFromResponse(response)
                    NetworkErrorManager.sendError(AppError.ClientError(code, message))
                }
                in 500..599 -> {
                    NetworkErrorManager.sendError(AppError.ServerError(code))
                }
                else -> {
                    NetworkErrorManager.sendError(AppError.Unknown)
                }
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

    private fun getErrorMessageFromResponse(response: Response): String? {
        return try {
            val errorBody = response.peekBody(Long.MAX_VALUE).string()
            JSONObject(errorBody).optString(MESSAGE).takeIf { it.isNotBlank() }
        } catch (e: Exception) {
            null
        }
    }
}