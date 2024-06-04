package com.example.data.source.remote

class ApiRemoteSource {

    suspend fun loginUser(
        login: String,
        password: String
    ): Result<String> {
        TODO("LOGIN USER")
    }

    suspend fun registrationUser(
        login: String,
        password: String
    ): Result<String> {
        TODO("REGISTRATION USER")

        return loginUser(login = login, password = password)
    }

}