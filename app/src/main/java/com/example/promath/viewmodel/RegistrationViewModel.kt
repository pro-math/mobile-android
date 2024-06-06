package com.example.promath.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ResultModel
import com.example.domain.usecase.RegistrationUserUseCase
import com.example.domain.usecase.SetTokenToLocalStorageUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val registrationUserUseCase: RegistrationUserUseCase,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
): ViewModel() {

    private val _registrationResult: MutableLiveData<ResultModel<String>> = MutableLiveData()
    val registrationResult: LiveData<ResultModel<String>> = _registrationResult

    fun registrationUser(
        login: String,
        password: String
    ) {
        viewModelScope.launch {
            val result = registrationUserUseCase.registration(login = login, password = password)

            _registrationResult.postValue(result)

            if (result.status == ResultModel.Status.SUCCESS) {
                setTokenToLocalStorageUseCase.set(result.data!!)
            }
        }
    }

}