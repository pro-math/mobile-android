package com.example.domain.usecase

import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel
import com.example.domain.repository.ApiRepository

class GetUserUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun execute(token: String): ResultModel<UserModel> = apiRepository.getUser(token = token)

}