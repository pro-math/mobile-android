package com.example.promath.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.generationOfExamples.ExampleGenerator
import com.example.generationOfExamples.ExampleModel
import com.example.generationOfExamples.TypeOperation
// писька

class MainViewModel(
    private val exampleGenerator: ExampleGenerator
): ViewModel() {

    private val _example: MutableLiveData<ExampleModel> = MutableLiveData()
    val example: LiveData<ExampleModel> = _example

    private val _countSuccessAnswer: MutableLiveData<Int> = MutableLiveData(0)
    val countSuccessAnswer: LiveData<Int> = _countSuccessAnswer
    private val _countExamples: MutableLiveData<Int> = MutableLiveData(0)
    val countExamples: LiveData<Int> = _countExamples

    val currentDifficulty: MutableLiveData<Int> = MutableLiveData(0)
    val currentOperation: MutableLiveData<Int> = MutableLiveData(0)
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

    fun addCountExample() {
        _countExamples.postValue(_countExamples.value!! + 1)
    }

    fun loadExample() {
        _example.postValue(exampleGenerator.generateExample(
            typeOperation = when (currentOperation.value) {
                0 -> TypeOperation.PLUS
                1 -> TypeOperation.MINUS
                2 -> TypeOperation.MULTIPLY
                3 -> TypeOperation.DIVIDE
                else -> TypeOperation.PLUS
            },
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
        ))
//        _countExamples.postValue(_countExamples.value!! + 1)
    }

    fun clearAnswers() {
        timer.cancel()
        isStopGame.postValue(false)
        _countExamples.postValue(0)
        _countSuccessAnswer.postValue(0)
    }

}