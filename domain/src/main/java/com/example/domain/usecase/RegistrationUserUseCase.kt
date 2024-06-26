package com.example.domain.usecase

import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel
import com.example.domain.repository.ApiRepository

class RegistrationUserUseCase(
    private val apiRepository: ApiRepository
) {

    suspend fun registration(
        login: String,
        password: String
    ): ResultModel<String> {
//        return ResultModel.success(data = "test")

        return apiRepository.registrationUser(login = login, password = password)
    }

}