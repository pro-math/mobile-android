package com.example.promath.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.SetTokenToLocalStorageUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
): ViewModel() {

    private val _userData: MutableLiveData<UserModel?> = MutableLiveData()
    val userData: LiveData<UserModel?> = _userData

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String> = _username

    fun logoutUser() {
        setTokenToLocalStorageUseCase.set("")
        loadUserData()
    }

    fun loadUserData() {
        val token = getTokenFromLocalStorageUseCase.execute()

        if (token.status == ResultModel.Status.SUCCESS) {
            viewModelScope.launch {
                Log.i("TEST_PROFILE", token.data.toString())
                val user = getUserUseCase.execute(token.data!!)
                if (user.status == ResultModel.Status.SUCCESS) {
                    _userData.postValue(user.data!!)
                    _username.postValue(user.data!!.login)
                } else {
                    _username.postValue(user.message.toString())
                }

            }
        } else {
            _username.postValue("username")
            _userData.postValue(null)
        }
    }

}