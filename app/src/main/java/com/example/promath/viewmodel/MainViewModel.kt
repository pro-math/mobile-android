package com.example.promath.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.generationOfExamples.ExampleGenerator
import com.example.generationOfExamples.ExampleModel
import com.example.generationOfExamples.TypeOperation

class MainViewModel(
    private val exampleGenerator: ExampleGenerator
): ViewModel() {

    private val _example: MutableLiveData<ExampleModel> = MutableLiveData()
    val example: LiveData<ExampleModel> = _example

    fun loadExample() {
        _example.postValue(exampleGenerator.generateExample(typeOperation = TypeOperation.PLUS, border1 = 1, border2 = 100))
    }

}