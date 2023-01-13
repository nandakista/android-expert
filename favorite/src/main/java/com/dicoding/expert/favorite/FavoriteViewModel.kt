package com.dicoding.expert.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    val getFavUser by lazy {
        userUseCase.getFavoriteUser().asLiveData()
    }
}