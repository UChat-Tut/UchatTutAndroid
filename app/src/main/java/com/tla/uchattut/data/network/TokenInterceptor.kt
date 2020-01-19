package com.tla.uchattut.data.network

import com.tla.uchattut.data.repositories.auth.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    companion object {
        private const val TOKEN_HEADER = "Authorization"
    }

    private val authRepository = AuthRepository()

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = authRepository.getAuthToken()
        if (token?.isNotBlank() == true) {
            requestBuilder.addHeader(TOKEN_HEADER, token)
        } else {
            openLoginActivity()
        }
        return chain.proceed(requestBuilder.build())
    }

    private fun openLoginActivity() {
        /*val intent = Intent(BasicApp.context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        BasicApp.context.startActivity(intent)*/
    }

}