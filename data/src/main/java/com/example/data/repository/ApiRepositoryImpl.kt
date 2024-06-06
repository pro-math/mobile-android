package com.example.data.repository

import com.example.data.models.LoginModel
import com.example.data.source.remote.ApiRemoteSource
import com.example.domain.models.GameSession
import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel
import com.example.domain.repository.ApiRepository

class ApiRepositoryImpl(
    private val apiRemoteSource: ApiRemoteSource
): ApiRepository {

    override suspend fun loginUser(login: String, password: String): ResultModel<String> {
        return apiRemoteSource.loginUser(login = login, password = password)
    }

    override suspend fun registrationUser(login: String, password: String): ResultModel<String> {
        return apiRemoteSource.registrationUser(login = login, password = password)
    }

    override suspend fun getUser(token: String): ResultModel<UserModel> = apiRemoteSource.getUser(token = token)
    override suspend fun createGameSession(gameSession: GameSession, token: String) {
        apiRemoteSource.postGameSession(gameSession, token)
    }

}