package com.dicoding.expert.ui.pages.home

import androidx.lifecycle.*
import com.dicoding.expert.domain.usecases.UserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class   HomeViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    val getAllUser by lazy {
        userUseCase.getAllUser().asLiveData()
    }
    val queryChannel = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult = queryChannel
        .debounce(1000).distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest {
            userUseCase.searchUser(it).asLiveData()
        }.asLiveData()
}