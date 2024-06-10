package com.example.promath.di

import com.example.data.repository.ApiRepositoryImpl
import com.example.data.repository.LocalStorageRepositoryImpl
import com.example.domain.usecase.CreateGameSessionUseCase
import com.example.domain.usecase.DeleteUserUseCase
import com.example.domain.usecase.GenerateExampleUseCase
import com.example.domain.usecase.GetAllAchievementUseCase
import com.example.domain.usecase.GetCurrentThemeUseCase
import com.example.domain.usecase.GetProgressUseCase
import com.example.domain.usecase.GetRatingListUseCase
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.domain.usecase.GetUserAchievementUseCase
import com.example.domain.usecase.GetUserRatingUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegistrationUserUseCase
import com.example.domain.usecase.SetThemeToLocalStorageUseCase
import com.example.domain.usecase.SetTokenToLocalStorageUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GenerateExampleUseCase> { GenerateExampleUseCase() }

    factory<GetTokenFromLocalStorageUseCase> { GetTokenFromLocalStorageUseCase(localStorageRepository = get<LocalStorageRepositoryImpl>()) }

    factory<LoginUserUseCase> { LoginUserUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<RegistrationUserUseCase> { RegistrationUserUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<SetTokenToLocalStorageUseCase> { SetTokenToLocalStorageUseCase(localStorageRepository = get<LocalStorageRepositoryImpl>()) }

    factory<GetUserUseCase> { GetUserUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<CreateGameSessionUseCase> { CreateGameSessionUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<GetCurrentThemeUseCase> { GetCurrentThemeUseCase(localStorageRepository = get<LocalStorageRepositoryImpl>()) }

    factory<SetThemeToLocalStorageUseCase> { SetThemeToLocalStorageUseCase(localStorageRepository = get<LocalStorageRepositoryImpl>()) }

    factory<GetRatingListUseCase> { GetRatingListUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<GetUserRatingUseCase> { GetUserRatingUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<DeleteUserUseCase> { DeleteUserUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<GetProgressUseCase> { GetProgressUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<GetAllAchievementUseCase> { GetAllAchievementUseCase(apiRepository = get<ApiRepositoryImpl>()) }

    factory<GetUserAchievementUseCase> { GetUserAchievementUseCase(apiRepository = get<ApiRepositoryImpl>()) }

}