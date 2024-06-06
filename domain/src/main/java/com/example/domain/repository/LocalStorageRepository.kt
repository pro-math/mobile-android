package com.example.domain.repository

import com.example.domain.models.ResultModel

interface LocalStorageRepository {

    fun getTokenFromLocalStorage(): ResultModel<String>

    fun setTokenToLocalStorage(token: String)

    fun getCurrentTheme(): ResultModel<String>

    fun setThemeToLocalStorage(theme: String)

}