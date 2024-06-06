package com.example.domain.usecase

import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel
import com.example.domain.repository.ApiRepository

class LoginUserUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun login(
        login: String,
        password: String
    ): ResultModel<String> = apiRepository.loginUser(login = login, password = password)

}