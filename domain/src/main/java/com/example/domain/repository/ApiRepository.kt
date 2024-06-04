package com.example.domain.repository

interface ApiRepository {

    suspend fun loginUser(
        login: String,
        password: String
    ): Result<String>

    suspend fun registrationUser(
        login: String,
        password: String
    ): Result<String>

}