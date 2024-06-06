package com.example.domain.usecase

import com.example.domain.models.GameSession
import com.example.domain.repository.ApiRepository

class CreateGameSessionUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun create(gameSession: GameSession, token: String) {
        apiRepository.createGameSession(gameSession, token)
    }

}