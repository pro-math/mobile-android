package com.example.promath.di

import com.example.generationOfExamples.ExampleGenerator
import org.koin.dsl.module

val generationOfExamplesModule = module {

    factory<ExampleGenerator> {
        ExampleGenerator()
    }

}