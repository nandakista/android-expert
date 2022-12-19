package com.dicoding.expert.ui.pages.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.expert.domain.usecases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    val getFavUser by lazy {
        userUseCase.getFavoriteUser().asLiveData()
    }
}