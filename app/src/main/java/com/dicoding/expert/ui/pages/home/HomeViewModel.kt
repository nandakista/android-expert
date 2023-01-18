package com.dicoding.expert.ui.pages.home

import androidx.lifecycle.*
import com.dicoding.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    val getAllUser by lazy {
        userUseCase.getAllUser().asLiveData()
    }
}