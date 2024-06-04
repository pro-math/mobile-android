package com.example.data.repository

import com.example.data.source.remote.ApiRemoteSource
import com.example.domain.repository.ApiRepository

class ApiRepositoryImpl(
    private val apiRemoteSource: ApiRemoteSource
): ApiRepository {

    override suspend fun loginUser(login: String, password: String): Result<String> {
        return apiRemoteSource.loginUser(login = login, password = password)
    }

    override suspend fun registrationUser(login: String, password: String): Result<String> {
        return apiRemoteSource.registrationUser(login = login, password = password)
    }

}