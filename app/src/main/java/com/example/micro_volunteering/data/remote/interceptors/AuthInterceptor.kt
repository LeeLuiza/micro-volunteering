package com.example.micro_volunteering.data.remote.interceptors

import com.example.micro_volunteering.data.local.TokenPreferences
import com.example.micro_volunteering.data.constants.AppConstants.HEADER_AUTHORIZATION
import com.example.micro_volunteering.data.constants.AppConstants.HEADER_BEARER_PREFIX
import com.example.micro_volunteering.data.constants.AppConstants.NO_AUTH_HEADER
import com.example.micro_volunteering.data.remote.api.VolunteeringApiService
import com.example.micro_volunteering.data.remote.dto.request.RefreshTokenRequest
import okhttp3.Interceptor
import javax.inject.Provider
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

class AuthInterceptor(
    private val tokenManager: TokenPreferences,
    private val apiService: Provider<VolunteeringApiService>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().build()

        if (request.header(NO_AUTH_HEADER) != null) {
            return chain.proceed(
                request.newBuilder()
                    .removeHeader(NO_AUTH_HEADER)
                    .build()
            )
        }

        synchronized(this) {
            val token = tokenManager.getAccessToken()

            return if (token != null) {
                val response = addTokenToRequest(chain, request, token)

                if (response.code != HttpURLConnection.HTTP_UNAUTHORIZED) {
                    response
                }
                else {
                    response.close()
                    refreshAndRetry(chain, request)
                }
            }
            else {
                refreshAndRetry(chain, request)
            }
        }
    }

    private fun addTokenToRequest(chain: Interceptor.Chain, request: Request, token: String): Response {
        val newRequest = request.newBuilder()
            .header(HEADER_AUTHORIZATION, HEADER_BEARER_PREFIX + token)
            .build()
        return chain.proceed(newRequest)
    }

    private fun refreshAndRetry(chain: Interceptor.Chain, request: Request) : Response {
        val refreshToken = tokenManager.getRefreshToken()

        if (refreshToken == null) {
            tokenManager.deleteToken()
            return chain.proceed(request)
        }

        try {
            val call = apiService.get().refreshToken(RefreshTokenRequest(refreshToken))
            val refreshResponse = call.execute()

            if (refreshResponse.isSuccessful && refreshResponse.body() != null) {
                val newTokens = refreshResponse.body()!!
                tokenManager.saveTokens(newTokens.accessToken, newTokens.refreshToken)

                return addTokenToRequest(chain, request, newTokens.accessToken)
            } else {
                tokenManager.deleteToken()
                return chain.proceed(request)
            }
        }
        catch (e: Exception) {
            tokenManager.deleteToken()
            return chain.proceed(request)
        }
    }
}