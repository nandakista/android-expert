package com.dicoding.expert.ui.pages.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.expert.domain.usecases.UserUseCase

class FavoriteViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    val getFavUser by lazy {
        userUseCase.getFavoriteUser().asLiveData()
    }
}