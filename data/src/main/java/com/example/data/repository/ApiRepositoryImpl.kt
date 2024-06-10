package com.example.data.repository

import com.example.data.source.remote.ApiRemoteSource
import com.example.domain.models.AchievementModel
import com.example.domain.models.GameMode
import com.example.domain.models.GameSession
import com.example.domain.models.ProgressModel
import com.example.domain.models.RatingElementModel
import com.example.domain.models.ResultModel
import com.example.domain.models.UserHistoryElementModel
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

    override suspend fun getRatingList(
        gameMode: GameMode,
        examplesCategory: Int,
        mathOperations: List<String>,
        difficulty: Int,
        limit: Int,
        offset: Int
    ): ResultModel<List<RatingElementModel>> {
        return apiRemoteSource.getListRating(
            gameMode = gameMode,
            examplesCategory = examplesCategory,
            mathOperations = mathOperations,
            difficulty = difficulty,
            limit = limit,
            offset = offset
        )
    }

    override suspend fun getUserRatingList(
        token: String,
        gameMode: GameMode,
        examplesCategory: Int,
        mathOperations: List<String>,
        difficulty: Int,
        limit: Int,
        offset: Int
    ): ResultModel<List<UserHistoryElementModel>> {
        return apiRemoteSource.getListUserRating(
            token, gameMode, examplesCategory, mathOperations, limit, offset, difficulty
        )
    }

    override suspend fun deleteUser(token: String) {
        apiRemoteSource.deleteUser(token = token)
    }

    override suspend fun getProgressUser(
        token: String,
        gameMode: GameMode,
        examplesCategory: Int,
        difficulty: Int,
        mathOperations: List<String>
    ): ResultModel<List<ProgressModel>> {
        return apiRemoteSource.getProgressUser(
            token = token,
            gameMode = gameMode,
            examplesCategory = examplesCategory,
            difficulty = difficulty,
            mathOperations = mathOperations
        )
    }

    override suspend fun getAllAchievement(): ResultModel<List<AchievementModel>> {
        return apiRemoteSource.getAllAchievement()
    }

    override suspend fun getUserAchievement(token: String): ResultModel<List<AchievementModel>> {
        return apiRemoteSource.getUserAchievement(token)
    }

}