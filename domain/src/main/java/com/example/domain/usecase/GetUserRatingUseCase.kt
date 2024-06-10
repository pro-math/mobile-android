package com.example.domain.usecase

import com.example.domain.models.GameMode
import com.example.domain.models.ResultModel
import com.example.domain.models.UserHistoryElementModel
import com.example.domain.repository.ApiRepository

class GetUserRatingUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun execute(token: String, gameMode: GameMode, examplesCategory: Int, mathOperations: List<String>, difficulty: Int, limit: Int, offset: Int): ResultModel<List<UserHistoryElementModel>> = apiRepository.getUserRatingList(
        token = token,
        gameMode = gameMode,
        examplesCategory = examplesCategory,
        mathOperations = mathOperations,
        difficulty = difficulty,
        limit = limit,
        offset = offset
    )

}