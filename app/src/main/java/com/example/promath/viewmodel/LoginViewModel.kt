package com.example.promath.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ResultModel
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegistrationUserUseCase
import com.example.domain.usecase.SetTokenToLocalStorageUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
): ViewModel() {

    private val _loginResult: MutableLiveData<ResultModel<String>> = MutableLiveData()
    val loginResult: LiveData<ResultModel<String>> = _loginResult

    fun loginUser(
        login: String,
        password: String
    ) {
        viewModelScope.launch {
            val result = loginUserUseCase.login(login = login, password = password)

            _loginResult.postValue(result)

            if(result.status == ResultModel.Status.SUCCESS) {
                setTokenToLocalStorageUseCase.set(result.data!!)
            }
        }
    }

}