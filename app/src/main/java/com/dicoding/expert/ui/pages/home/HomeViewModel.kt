package com.dicoding.expert.ui.pages.home

import androidx.lifecycle.*
import com.dicoding.expert.domain.usecases.UserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    val getAllUser by lazy {
        userUseCase.getAllUser().asLiveData()
    }
    val queryChannel = MutableStateFlow("")
    val searchResult = queryChannel
        .debounce(1000).distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest {
            userUseCase.searchUser(it).asLiveData()
        }.asLiveData()
}