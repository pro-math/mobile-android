package com.example.domain.usecase

import com.example.domain.repository.LocalStorageRepository

class SetTokenToLocalStorageUseCase(
    private val localStorageRepository: LocalStorageRepository
) {

    fun set(token: String) {
        localStorageRepository.setTokenToLocalStorage(token = token)
    }

}