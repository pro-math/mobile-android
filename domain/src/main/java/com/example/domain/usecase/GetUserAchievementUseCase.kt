package com.example.domain.usecase

import com.example.domain.repository.ApiRepository

class GetUserAchievementUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun execute(token: String) = apiRepository.getUserAchievement(token)

}