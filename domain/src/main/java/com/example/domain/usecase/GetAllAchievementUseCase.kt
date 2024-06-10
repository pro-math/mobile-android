package com.example.domain.usecase

import com.example.domain.repository.ApiRepository

class GetAllAchievementUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun execute() = apiRepository.getAllAchievement()

}