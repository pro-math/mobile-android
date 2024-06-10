package com.example.promath.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.GameMode
import com.example.domain.models.RatingElementModel
import com.example.domain.models.ResultModel
import com.example.domain.models.UserHistoryElementModel
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.domain.usecase.GetUserRatingUseCase
import kotlinx.coroutines.launch

class UserRatingViewModel(
    private val getUserRatingUseCase: GetUserRatingUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
) : ViewModel() {

    val isOpenDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val openDialogElement: MutableLiveData<UserHistoryElementModel> = MutableLiveData()

    val currentDifficulty: MutableLiveData<Int> = MutableLiveData(0)
    val currentOperation: MutableLiveData<MutableList<Boolean>> = MutableLiveData(mutableListOf(true, true, true, true))
    val currentType: MutableLiveData<Int> = MutableLiveData(0)

    val currentTime: MutableLiveData<Int> = MutableLiveData(0)
    val currentCount: MutableLiveData<Int> = MutableLiveData(0)

    val offset: Int = 0

    private val _listRating: MutableLiveData<List<UserHistoryElementModel>> = MutableLiveData(emptyList())
    val listRating: LiveData<List<UserHistoryElementModel>> = _listRating

    fun loadRating() {
        val token = getTokenFromLocalStorageUseCase.execute()
        if (token.status == ResultModel.Status.SUCCESS) {
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

            viewModelScope.launch {
                val result = getUserRatingUseCase.execute(
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
                                else -> 100
                            }
                        }
                    },
                    limit = 50,
                    offset = offset
                )
                if (result.status == ResultModel.Status.SUCCESS) {
                    Log.i("TEST RATING", result.data.toString())
                    _listRating.postValue(result.data!!)
                } else {
                    Log.i("TEST RATING", result.message.toString())
                }

            }
        }

    }

    fun changedDataAndLoad(selectionName: String, i: Int) {
        when (selectionName) {
            "Difficulty" -> currentDifficulty.postValue(i)
            "Operations" -> {
                var operations: MutableList<Boolean> = mutableListOf(false, false, false, false)
                for (ind in currentOperation.value!!.indices) {
                    if (ind == i) {
                        operations[ind] = !currentOperation.value!![ind]
                    } else {
                        operations[ind] = currentOperation.value!![ind]
                    }
                }
                if (operations == mutableListOf(false, false, false, false)) {
                    operations = mutableListOf(true, true, true, true)
                }
                Log.i("TEST_OPERATIONS", operations.toString())
                currentOperation.postValue(operations)
            }
            "Type" -> currentType.postValue(i)
            "Time" -> currentTime.postValue(i)
            "Count" -> currentCount.postValue(i)
        }
        loadRating()
    }

}