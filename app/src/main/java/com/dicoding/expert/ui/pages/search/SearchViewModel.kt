package com.dicoding.expert.ui.pages.search

import androidx.lifecycle.*
import com.dicoding.expert.core.di.Injection
import com.dicoding.expert.data.repositories.SearchUserRepository

class SearchViewModel(private val searchUserRepository: SearchUserRepository) : ViewModel() {
    fun searchUser(username: String) = searchUserRepository.searchUser(username)
}

class SearchViewModelFactory private constructor(private val searchUserRepository: SearchUserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SearchViewModelFactory? = null
        fun getInstance(): SearchViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SearchViewModelFactory(Injection.searchUserRepository())
            }.also { instance = it }
    }
}