package com.tla.uchattut.data.network

import com.tla.uchattut.data.network.model.auth.TokenResponseNetworkModel
import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RestAuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): TokenResponseNetworkModel
}