package com.tla.uchattut.data.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://uchattut.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val networkApi = retrofit.create(RestApi::class.java)
}