package com.example.domain.repository

import com.example.domain.models.AchievementModel
import com.example.domain.models.GameMode
import com.example.domain.models.GameSession
import com.example.domain.models.ProgressModel
import com.example.domain.models.RatingElementModel
import com.example.domain.models.ResultModel
import com.example.domain.models.UserHistoryElementModel
import com.example.domain.models.UserModel
import java.time.ZoneOffset

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

    suspend fun getRatingList(gameMode: GameMode, examplesCategory: Int, mathOperations: List<String>, difficulty: Int, limit: Int, offset: Int): ResultModel<List<RatingElementModel>>

    suspend fun getUserRatingList(token: String, gameMode: GameMode, examplesCategory: Int, mathOperations: List<String>, difficulty: Int, limit: Int, offset: Int): ResultModel<List<UserHistoryElementModel>>

    suspend fun deleteUser(token: String)

    suspend fun getProgressUser(token: String, gameMode: GameMode, examplesCategory: Int, difficulty: Int, mathOperations: List<String>): ResultModel<List<ProgressModel>>

    suspend fun getAllAchievement(): ResultModel<List<AchievementModel>>

    suspend fun getUserAchievement(token: String): ResultModel<List<AchievementModel>>

}