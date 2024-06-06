package com.example.promath.di

import com.example.promath.viewmodel.LoginViewModel
import com.example.promath.viewmodel.MainViewModel
import com.example.promath.viewmodel.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MainViewModel> { MainViewModel(exampleGenerator = get(), createGameSessionUseCase = get(), getTokenFromLocalStorageUseCase = get()) }

    viewModel<LoginViewModel> { LoginViewModel(loginUserUseCase = get(), setTokenToLocalStorageUseCase = get()) }

    viewModel<RegistrationViewModel> { RegistrationViewModel(registrationUserUseCase = get(), setTokenToLocalStorageUseCase = get()) }

}