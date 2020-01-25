package com.tla.uchattut.data.network.model.auth

sealed class RegisterNetworkModel {

    data class Request(
        val email: String,
        val password: String,
        val name: String,
        val surname: String
    ) : RegisterNetworkModel()

    data class Response(
        val token: String
    ) : RegisterNetworkModel()
}