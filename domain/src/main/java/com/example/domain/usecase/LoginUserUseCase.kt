package com.example.domain.usecase

import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel

class LoginUserUseCase {

    fun login(
        login: String,
        password: String
    ): ResultModel<UserModel> {
        return if (MockUser.login == login && MockUser.password == password) {
            ResultModel.success(
                UserModel(
                    login = MockUser.login,
                    token = MockUser.password
                )
            )
        } else {
            ResultModel.failure(
                message = "Invalid login or password"
            )
        }
    }

}