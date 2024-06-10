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

    private val _time: MutableLiveData<Int> = MutableLiveData(0)
    val time: LiveData<Int> = _time

    val isStopGame: MutableLiveData<Boolean> = MutableLiveData(false)

    private var timerDown: CountDownTimer = object : CountDownTimer(30000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            _time.postValue((millisUntilFinished / 1000).toInt())
        }

        override fun onFinish() {
            Log.i("ON FINISH TIMER", "FINISH")
            createSession()
            isStopGame.postValue(true)
        }
    }

    val isStartCreateSession: MutableLiveData<Boolean> = MutableLiveData(false)

    var timerUp: CountDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            _time.postValue(_time.value!! + 1)
        }

        override fun onFinish() {
            isStopGame.postValue(true)
        }
    }

    private val examplesList: MutableList<com.example.domain.models.ExampleModel> = mutableListOf()

    fun startTimerTime() {
        val s = when (currentTime.value) {
            0 -> 15
            1 -> 30
            2 -> 60
            else -> 90
        }
        _time.postValue(s)
        Log.i("TIMER START", s.toString())


        timerDown = object : CountDownTimer((s * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _time.postValue((millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                createSession()
                isStopGame.postValue(true)
            }
        }

        timerDown.start()
    }

    fun startTimeCount() {
        timerUp.start()
    }

    fun addCountSuccessAnswer() {
        Log.i("TEST CREATE SESSION SUCCESS", _countSuccessAnswer.value.toString())
        _countSuccessAnswer.value = _countSuccessAnswer.value!! + 1
//        _countSuccessAnswer.postValue(_countSuccessAnswer.value!! + 1)
        Log.i("TEST CREATE SESSION SUCCESS", _countSuccessAnswer.value.toString())
    }

    fun addCountExample(exampleModel: ExampleModel, userAns: String) {
        _countExamples.value = _countExamples.value!! + 1
//        _countExamples.postValue(_countExamples.value!! + 1)
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
        Log.i("TEST CREATE SESSION ALL", _countExamples.value.toString())
    }

    fun createSession() {
        val token = getTokenFromLocalStorageUseCase.execute()
        val gameMode = if (currentType.value == 0) {
            GameMode.TimeMode
        } else {
            GameMode.CountMode
        }
        val duration = when (currentType.value) {
            0 -> {
                when (currentTime.value) {
                    0 -> 15
                    1 -> 30
                    2 -> 60
                    else -> 90
                }
            }
            else -> {
                if (time.value != null) {
                    (time.value!!).toLong()
                } else {
                    0
                }
            }
        }
        Log.i("TEST CREATE GAME SESSION", duration.toString())
        val mathOperations = buildList {
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
        }
        val examplesCategory = if (currentDifficulty.value == 0) {
            10
        } else if (currentDifficulty.value == 1) {
            15
        } else if (currentDifficulty.value == 2) {
            20
        } else {
            30
        }

        if (token.status == ResultModel.Status.SUCCESS) {
            viewModelScope.launch {
                createGameSessionUseCase.create(
                    gameSession = GameSession(
                        gameMode = gameMode,
                        duration = duration,
                        mathOperations = mathOperations,
                        examplesCategory = examplesCategory,
                        examples = examplesList,
                        totalCount = _countExamples.value!!,
                        correctCount = _countSuccessAnswer.value!!
                    ),
                    token = token.data!!
                )
                examplesList.clear()
            }
        }
        Log.i("TEST CREATE SESSION FINAL", "${_countExamples.value}, ${_countSuccessAnswer.value}")
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
//        addCountExample(exampleModel = example, userAns = "0")
//        _countExamples.postValue(_countExamples.value!! + 1)
    }

    fun clearAnswers() {
        timerUp.cancel()
        timerDown.cancel()
        _time.postValue(0)
        isStopGame.postValue(false)
        _countExamples.postValue(0)
        _countSuccessAnswer.postValue(0)
    }

}