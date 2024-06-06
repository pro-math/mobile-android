package com.example.data.repository

import com.example.data.source.local.LocalStorageSource
import com.example.domain.models.ResultModel
import com.example.domain.repository.LocalStorageRepository

class LocalStorageRepositoryImpl(
    private val localStorageSource: LocalStorageSource
): LocalStorageRepository {
    override fun getTokenFromLocalStorage(): ResultModel<String> = localStorageSource.getTokenFromLocalStorage()

    override fun setTokenToLocalStorage(token: String) {
        localStorageSource.setTokenToLocalStorage(token = token)
    }

    override fun getCurrentTheme(): ResultModel<String> = localStorageSource.getCurrentTheme()

}