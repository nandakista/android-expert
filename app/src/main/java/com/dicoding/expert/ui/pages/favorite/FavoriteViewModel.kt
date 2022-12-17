package com.dicoding.expert.ui.pages.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.expert.core.di.Injection
import com.dicoding.expert.data.repositories.LocalUserRepository

class FavoriteViewModel(private val localUserRepository: LocalUserRepository) : ViewModel() {
    val getFavUser by lazy {
        localUserRepository.getFavUser()
    }
}

class FavoriteViewModelFactory private constructor(private val localUserRepository: LocalUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(localUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null
        fun getInstance(context: Context): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.localUserRepository(context))
            }.also { instance = it }
    }
}