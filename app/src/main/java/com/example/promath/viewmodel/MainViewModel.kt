package com.example.promath.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.GameMode
import com.example.domain.models.GameSession
import com.example.domain.models.ResultModel
import com.example.domain.usecase.CreateGameSessionUseCase
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.generationOfExamples.ExampleGenerator
import com.example.generationOfExamples.ExampleModel
import com.example.generationOfExamples.TypeOperation
import kotlinx.coroutines.launch
import java.lang.Exception

// писька

class MainViewModel(
    private val exampleGenerator: ExampleGenerator,
    private val createGameSessionUseCase: CreateGameSessionUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
): ViewModel() {

    private val _example: MutableLiveData<ExampleModel> = MutableLiveData()
    val example: LiveData<ExampleModel> = _example

    private val _countSuccessAnswer: MutableLiveData<Int> = MutableLiveData(0)
    val countSuccessAnswer: LiveData<Int> = _countSuccessAnswer
    private val _countExamples: MutableLiveData<Int> = MutableLiveData(0)
    val countExamples: LiveData<Int> = _countExamples

    val currentDifficulty: MutableLiveData<Int> = MutableLiveData(0)
    val currentOperation: MutableLiveData<MutableList<Boolean>> = MutableLiveData(mutableListOf(true, true, true, true))
    val currentType: MutableLiveData<Int> = MutableLiveData(0)

    val currentTime: MutableLiveData<Int> = MutableLiveData(0)
    val currentCount: MutableLiveData<Int> = MutableLiveData(0)

    private val _time: MutableLiveData<Int> = MutableLiveData()
    val time: LiveData<Int> = _time

    val isStopGame: MutableLiveData<Boolean> = MutableLiveData(false)

    private var timer: CountDownTimer = object : CountDownTimer(30000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            _time.postValue((millisUntilFinished / 1000).toInt())
        }

        override fun onFinish() {
            isStopGame.postValue(true)
        }
    }

    private val examplesList: MutableList<com.example.domain.models.ExampleModel> = mutableListOf()

    fun startTimer() {
        val s = when (currentTime.value) {
            0 -> 30
            1 -> 60
            2 -> 90
            else -> 30
        }
        _time.postValue(s)
        Log.i("TIMER START", s.toString())


        timer = object : CountDownTimer((s * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _time.postValue((millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                isStopGame.postValue(true)
            }
        }

        timer.start()
    }

    fun addCountSuccessAnswer() {
        _countSuccessAnswer.postValue(_countSuccessAnswer.value!! + 1)
    }

    fun addCountExample(exampleModel: ExampleModel, userAns: String) {
        _countExamples.postValue(_countExamples.value!! + 1)
        examplesList.add(com.example.domain.models.ExampleModel(
            typeOperation = when (exampleModel.typeOperation) {
                TypeOperation.PLUS -> "+"
                TypeOperation.MINUS -> "-"
                TypeOperation.MULTIPLY -> "*"
                TypeOperation.DIVIDE -> "/"
            },
            number1 = exampleModel.number1,
            number2 = exampleModel.number2,
            correctAnswer = exampleModel.answer,
            answer = try {
                userAns.toInt()
            } catch (e: Exception) {
                -1
            }
        ))
    }

    fun createSession() {
        val token = getTokenFromLocalStorageUseCase.execute()

        if (token.status == ResultModel.Status.SUCCESS) {
            viewModelScope.launch {
                createGameSessionUseCase.create(
                    gameSession = GameSession(
                        gameMode = if (currentType.value == 0) {
                            GameMode.TimeMode
                        } else {
                            GameMode.CountMode
                        },
                        duration = 0,
                        mathOperations = buildList {
                            if (currentOperation.value!![0]) {
                                add("+")
                            }
                            if (currentOperation.value!![1]) {
                                add("-")
                            }
                            if (currentOperation.value!![2]) {
                                add("*")
                            }
                            if (currentOperation.value!![3]) {
                                add("/")
                            }
                        },
                        examplesCategory = if (currentDifficulty.value == 0) {
                            10
                        } else if (currentDifficulty.value == 1) {
                            100
                        } else {
                            1000
                        },
                        examples = examplesList,
                        totalCount = _countExamples.value!!,
                        correctCount = _countSuccessAnswer.value!!
                    ),
                    token = token.data!!
                )
            }
        }
    }

    fun loadExample() {
        val operationsList: List<TypeOperation> = buildList {
            if (currentOperation.value!![0]) {
                add(TypeOperation.PLUS)
            }
            if (currentOperation.value!![1]) {
                add(TypeOperation.MINUS)
            }
            if (currentOperation.value!![2]) {
                add(TypeOperation.MULTIPLY)
            }
            if (currentOperation.value!![3]) {
                add(TypeOperation.DIVIDE)
            }
        }
        val example = exampleGenerator.generateExample(
            typesOperations = operationsList,
            border1 = when (currentDifficulty.value) {
                0 -> 10
                1 -> 100
                2 -> 1000
                else -> 10
            },
            border2 = when (currentDifficulty.value) {
                0 -> 99
                1 -> 999
                2 -> 9999
                else -> 99
            }
        )
        _example.postValue(example)
//        _countExamples.postValue(_countExamples.value!! + 1)
    }

    fun clearAnswers() {
        timer.cancel()
        isStopGame.postValue(false)
        _countExamples.postValue(0)
        _countSuccessAnswer.postValue(0)
    }

}