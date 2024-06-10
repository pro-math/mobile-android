package com.example.promath.di

import com.example.promath.viewmodel.AchievementViewModel
import com.example.promath.viewmodel.LoginViewModel
import com.example.promath.viewmodel.MainViewModel
import com.example.promath.viewmodel.ProfileViewModel
import com.example.promath.viewmodel.ProgressViewModel
import com.example.promath.viewmodel.RatingViewModel
import com.example.promath.viewmodel.RegistrationViewModel
import com.example.promath.viewmodel.UserRatingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MainViewModel> { MainViewModel(exampleGenerator = get(), createGameSessionUseCase = get(), getTokenFromLocalStorageUseCase = get()) }

    viewModel<LoginViewModel> { LoginViewModel(loginUserUseCase = get(), setTokenToLocalStorageUseCase = get()) }

    viewModel<RegistrationViewModel> { RegistrationViewModel(registrationUserUseCase = get(), setTokenToLocalStorageUseCase = get()) }

    viewModel<ProfileViewModel> { ProfileViewModel(getTokenFromLocalStorageUseCase = get(), getUserUseCase = get(), setTokenToLocalStorageUseCase = get(), setThemeToLocalStorageUseCase = get(), getCurrentThemeUseCase = get(), deleteUserUseCase = get()) }

    viewModel<RatingViewModel> { RatingViewModel(getRatingListUseCase = get()) }

    viewModel<ProgressViewModel> { ProgressViewModel(getProgressUseCase = get(), getTokenFromLocalStorageUseCase = get()) }

    viewModel<UserRatingViewModel> { UserRatingViewModel(getUserRatingUseCase = get(), getTokenFromLocalStorageUseCase = get()) }

    viewModel<AchievementViewModel> { AchievementViewModel(getAllAchievementUseCase = get(), getUserAchievementUseCase = get(), getTokenFromLocalStorageUseCase = get()) }

}