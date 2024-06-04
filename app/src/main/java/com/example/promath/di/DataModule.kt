package com.example.promath.di

import com.example.data.repository.ApiRepositoryImpl
import com.example.data.repository.LocalStorageRepositoryImpl
import com.example.data.source.local.LocalStorageSource
import com.example.data.source.remote.ApiRemoteSource
import org.koin.dsl.module

val dataModule = module {

    single<LocalStorageRepositoryImpl> { LocalStorageRepositoryImpl(localStorageSource = get()) }

    single<LocalStorageSource> { LocalStorageSource(context = get()) }

    single<ApiRepositoryImpl> { ApiRepositoryImpl(apiRemoteSource = get()) }

    single<ApiRemoteSource> { ApiRemoteSource() }

}