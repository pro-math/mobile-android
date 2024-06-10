package com.example.promath.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AchievementModel
import com.example.domain.models.ResultModel
import com.example.domain.usecase.GetAllAchievementUseCase
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.domain.usecase.GetUserAchievementUseCase
import kotlinx.coroutines.launch

class AchievementViewModel(
    private val getAllAchievementUseCase: GetAllAchievementUseCase,
    private val getUserAchievementUseCase: GetUserAchievementUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
): ViewModel() {

    private val _allAchievement: MutableLiveData<List<AchievementModel>> = MutableLiveData(listOf())
    val allAchievement: LiveData<List<AchievementModel>> = _allAchievement

    private val _userAchievement: MutableLiveData<List<AchievementModel>> = MutableLiveData(listOf())
    val userAchievement: LiveData<List<AchievementModel>> = _userAchievement

    fun loadAll() {
        viewModelScope.launch {
            val res = getAllAchievementUseCase.execute()
            if (res.status == ResultModel.Status.SUCCESS) {
                _allAchievement.postValue(res.data!!)
            }
        }
    }

    fun loadUser() {
        val token = getTokenFromLocalStorageUseCase.execute()
        if (token.status == ResultModel.Status.SUCCESS) {
            viewModelScope.launch {
                val res = getUserAchievementUseCase.execute(token = token.data!!)
                if (res.status == ResultModel.Status.SUCCESS) {
                    _userAchievement.postValue(res.data!!)
                }
            }
        }
    }

}