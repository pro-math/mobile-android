package com.example.promath.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ResultModel
import com.example.domain.models.UserModel
import com.example.domain.usecase.DeleteUserUseCase
import com.example.domain.usecase.GetCurrentThemeUseCase
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.SetThemeToLocalStorageUseCase
import com.example.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.promath.ui.themenew.palette
import com.example.promath.ui.themenew.paletteCupcake
import com.example.promath.ui.themenew.paletteDark
import com.example.promath.ui.themenew.paletteEmerald
import com.example.promath.ui.themenew.paletteValentine
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase,
    private val setThemeToLocalStorageUseCase: SetThemeToLocalStorageUseCase,
    private val getCurrentThemeUseCase: GetCurrentThemeUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
): ViewModel() {

    private val _userData: MutableLiveData<UserModel?> = MutableLiveData()
    val userData: LiveData<UserModel?> = _userData

    private val _username: MutableLiveData<String?> = MutableLiveData()
    val username: LiveData<String?> = _username

    val currentThemeId: MutableLiveData<Int> = MutableLiveData(0)

    val updateTheme: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setTheme(theme: String) {
        setThemeToLocalStorageUseCase.set(theme)

        val getTheme = getCurrentThemeUseCase.execute()
        if (getTheme.status == ResultModel.Status.SUCCESS) {
            when (getTheme.data) {
                "dark" -> {
                    palette = paletteDark
                    currentThemeId.postValue(0)
                }
                "cupcake" -> {
                    palette = paletteCupcake
                    currentThemeId.postValue(1)
                }
                "valentine" -> {
                    palette = paletteValentine
                    currentThemeId.postValue(2)
                }
                "emerald" -> {
                    palette = paletteEmerald
                    currentThemeId.postValue(3)
                }
                else -> {
                    palette = paletteDark
                    currentThemeId.postValue(0)
                }
            }
        }
    }

    fun deleteUser() {
        val token = getTokenFromLocalStorageUseCase.execute()
        if (token.status == ResultModel.Status.SUCCESS) {
            viewModelScope.launch {
                deleteUserUseCase.delete(token = token.data!!)
                logoutUser()
            }
        }
    }

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
                    _username.postValue(null)
                }

            }
        } else {
            _username.postValue(null)
            _userData.postValue(null)
        }
    }

}