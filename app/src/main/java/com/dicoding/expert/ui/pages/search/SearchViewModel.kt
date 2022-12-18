package com.dicoding.expert.ui.pages.search

import androidx.lifecycle.*
import com.dicoding.expert.domain.usecases.UserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    val queryChannel = MutableStateFlow("")
    val searchResult = queryChannel
        .debounce(1000).distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest {
            userUseCase.searchUser(it).asLiveData()
        }.asLiveData()
}