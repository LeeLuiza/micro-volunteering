package com.example.micro_volunteering.data.remote.interceptors

import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.data.constants.AuthConstants.HEADER_AUTHORIZATION
import com.example.micro_volunteering.data.constants.AuthConstants.HEADER_BEARER_PREFIX
import com.example.micro_volunteering.data.constants.AuthConstants.NO_AUTH_HEADER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

class AuthInterceptor(
    private val tokenManager: TokenManager
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
        return TODO("Provide the return value")
    }
}