package com.example.domain.usecase

import com.example.domain.repository.LocalStorageRepository

class SetThemeToLocalStorageUseCase(
    private val localStorageRepository: LocalStorageRepository
) {

    fun set(theme: String) {
        localStorageRepository.setThemeToLocalStorage(theme = theme)
    }

}