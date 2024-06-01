package com.example.promath.di

import com.example.data.repository.LocalStorageRepositoryImpl
import com.example.data.source.local.LocalStorageSource
import org.koin.dsl.module

val dataModule = module {

    single<LocalStorageRepositoryImpl> { LocalStorageRepositoryImpl(localStorageSource = get()) }

    single<LocalStorageSource> { LocalStorageSource(context = get()) }

}