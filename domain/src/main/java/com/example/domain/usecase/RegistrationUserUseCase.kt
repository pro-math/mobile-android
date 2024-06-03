package com.example.domain.usecase

import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel

class RegistrationUserUseCase {

    fun registration(
        login: String,
        password: String
    ): ResultModel<String> {
        MockUser.login = login
        MockUser.password = password

        return ResultModel.success(password)
    }

}