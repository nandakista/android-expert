package com.dicoding.expert.ui.pages.search

import androidx.lifecycle.*
import com.dicoding.expert.domain.usecases.UserUseCase

class SearchViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun searchUser(username: String) = userUseCase.searchUser(username).asLiveData()
}