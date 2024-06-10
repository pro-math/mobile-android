package com.example.promath.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.GameMode
import com.example.domain.models.ProgressModel
import com.example.domain.models.ResultModel
import com.example.domain.usecase.GetProgressUseCase
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val getProgressUseCase: GetProgressUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
): ViewModel() {

    val currentDifficulty: MutableLiveData<Int> = MutableLiveData(0)
    val currentOperation: MutableLiveData<MutableList<Boolean>> = MutableLiveData(mutableListOf(true, true, true, true))
    val currentType: MutableLiveData<Int> = MutableLiveData(0)

    val currentTime: MutableLiveData<Int> = MutableLiveData(0)
    val currentCount: MutableLiveData<Int> = MutableLiveData(0)

    private val _data: MutableLiveData<List<ProgressModel>> = MutableLiveData()
    val data: LiveData<List<ProgressModel>> = _data

    fun loadData() {

        val mathOperations: MutableList<String> = mutableListOf()
        if (currentOperation.value!![0]) {
            mathOperations.add("+")
        }
        if (currentOperation.value!![1]) {
            mathOperations.add("-")
        }
        if (currentOperation.value!![2]) {
            mathOperations.add("*")
        }
        if (currentOperation.value!![3]) {
            mathOperations.add("/")
        }
        val token = getTokenFromLocalStorageUseCase.execute()
        if (token.status == ResultModel.Status.SUCCESS) {
            viewModelScope.launch {
                val res = getProgressUseCase.execute(
                    token = token.data!!,
                    gameMode = when (currentType.value) {
                        0 -> GameMode.TimeMode
                        1 -> GameMode.CountMode
                        else -> GameMode.TimeMode
                    },
                    examplesCategory = when (currentDifficulty.value) {
                        0 -> 10
                        1 -> 100
                        2 -> 1000
                        else -> 10
                    },
                    mathOperations = mathOperations,
                    difficulty = when (currentType.value) {
                        0 -> {
                            when (currentTime.value) {
                                0 -> 15
                                1 -> 30
                                2 -> 60
                                else -> 90
                            }
                        }
                        else -> {
                            when (currentCount.value) {
                                0 -> 10
                                1 -> 15
                                2 -> 20
                                else -> 30
                            }
                        }
                    }
                )
                if (res.status == ResultModel.Status.SUCCESS) {
                    Log.i("TEST PROGRESS", res.data.toString())
                    Log.i("TEST PROGRESS", mathOperations.toString())
                    _data.postValue(res.data!!)
                } else {
                    Log.i("TEST PROGRESS", res.message.toString())
                }
            }
        }

    }

}