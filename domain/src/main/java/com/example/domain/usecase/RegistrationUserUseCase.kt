package com.example.domain.usecase

import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel

class RegistrationUserUseCase {

    fun registration(
        login: String,
        password: String
    ): ResultModel<UserModel> {
        MockUser.login = login
        MockUser.password = password

        return ResultModel.success(UserModel(
            login = login,
            token = password
        ))
    }

}