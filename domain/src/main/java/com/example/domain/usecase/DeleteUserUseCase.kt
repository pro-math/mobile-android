package com.example.domain.usecase

import com.example.domain.repository.ApiRepository

class DeleteUserUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun delete(token: String) {
        apiRepository.deleteUser(token = token)
    }

}