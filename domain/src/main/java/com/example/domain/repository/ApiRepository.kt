package com.example.domain.repository

import com.example.domain.models.GameSession
import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel

interface ApiRepository {

    suspend fun loginUser(
        login: String,
        password: String
    ): ResultModel<String>

    suspend fun registrationUser(
        login: String,
        password: String
    ): ResultModel<String>

    suspend fun getUser(token: String): ResultModel<UserModel>

    suspend fun createGameSession(gameSession: GameSession, token: String)

}