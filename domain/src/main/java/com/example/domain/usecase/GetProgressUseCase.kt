package com.example.domain.usecase

import com.example.domain.models.GameMode
import com.example.domain.models.ProgressModel
import com.example.domain.models.ResultModel
import com.example.domain.repository.ApiRepository

class GetProgressUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun execute(token: String, gameMode: GameMode, examplesCategory: Int, difficulty: Int, mathOperations: List<String>): ResultModel<List<ProgressModel>> = apiRepository.getProgressUser(
        token = token,
        gameMode = gameMode,
        examplesCategory = examplesCategory,
        difficulty = difficulty,
        mathOperations = mathOperations
    )

}