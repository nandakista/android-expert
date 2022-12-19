package com.dicoding.expert.ui.pages.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.expert.domain.usecases.UserUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    val getFavUser by lazy {
        userUseCase.getFavoriteUser().asLiveData()
    }
}