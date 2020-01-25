package com.tla.uchattut.data.network

import android.content.Context
import android.content.Intent
import com.tla.uchattut.App
import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.presentation.auth.view.AuthActivity
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(): Interceptor {

    companion object {
        private const val TOKEN_HEADER = "Authorization"
    }

    private val authPreserences =
        App.context.getSharedPreferences(AuthRepository.AUTH_PREF_NAME, Context.MODE_PRIVATE)

    private fun getAuthToken(): String? {
        return authPreserences.getString(AuthRepository.TOKEN_PREF_KEY, null)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = getAuthToken()
        if (token?.isNotBlank() == true) {
            requestBuilder.addHeader(TOKEN_HEADER, "Token $token")
        } else {
            openAuthActivity()
        }
        return chain.proceed(requestBuilder.build())
    }

    private fun openAuthActivity() {
        val intent = Intent(App.context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        App.context.startActivity(intent)
    }

}