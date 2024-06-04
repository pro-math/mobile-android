package com.example.promath.di

import com.example.data.repository.LocalStorageRepositoryImpl
import com.example.domain.usecase.GenerateExampleUseCase
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegistrationUserUseCase
import com.example.domain.usecase.SetTokenToLocalStorageUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GenerateExampleUseCase> { GenerateExampleUseCase() }

    factory<GetTokenFromLocalStorageUseCase> { GetTokenFromLocalStorageUseCase(localStorageRepository = get<LocalStorageRepositoryImpl>()) }

    factory<LoginUserUseCase> { LoginUserUseCase(apiRepository = get()) }

    factory<RegistrationUserUseCase> { RegistrationUserUseCase(apiRepository = get()) }

    factory<SetTokenToLocalStorageUseCase> { SetTokenToLocalStorageUseCase(localStorageRepository = get<LocalStorageRepositoryImpl>()) }

}