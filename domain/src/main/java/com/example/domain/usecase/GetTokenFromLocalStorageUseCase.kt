package com.example.domain.usecase

import com.example.domain.models.ResultModel
import com.example.domain.repository.LocalStorageRepository

class GetTokenFromLocalStorageUseCase(
    private val localStorageRepository: LocalStorageRepository
) {

    fun execute(): ResultModel<String> = localStorageRepository.getTokenFromLocalStorage()

}