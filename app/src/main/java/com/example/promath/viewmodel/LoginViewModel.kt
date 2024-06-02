package com.example.promath.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.models.ResultModel
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.SetTokenToLocalStorageUseCase

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
): ViewModel() {

    fun loginUser(
        login: String,
        password: String
    ) {
        val result = loginUserUseCase.login(login = login, password = password)

        if(result.status == ResultModel.Status.SUCCESS) {
            setTokenToLocalStorageUseCase.set(result.data!!.token)
        } else {
//            return result.message.toString()
        }
    }

}