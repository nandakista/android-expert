package com.dicoding.expert.ui.pages.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.expert.core.database.entity.UserEntity
import com.dicoding.expert.core.di.Injection
import com.dicoding.expert.data.repositories.DetailUserRepository
import com.dicoding.expert.data.repositories.LocalUserRepository
import kotlinx.coroutines.launch

class DetailUserViewModel(
    private val detailUserRepository: DetailUserRepository,
    private val localUserRepository: LocalUserRepository,
) : ViewModel() {
    fun getDetailUser(username: String) = detailUserRepository.getDetailUser(username)

    suspend fun hasAdded(id: Int): Boolean = localUserRepository.hasAdded(id)
    fun deletedFavUser(id: Int) = viewModelScope.launch { localUserRepository.deleteFavUser(id) }
    fun addFavUser(user: UserEntity) {
        viewModelScope.launch { localUserRepository.setFavUser(user) }
    }
}

class DetailViewModelFactory private constructor(
    private val detailUserRepository: DetailUserRepository,
    private val localUserRepository: LocalUserRepository,
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(detailUserRepository, localUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetailViewModelFactory? = null
        fun getInstance(context: Context): DetailViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(
                    Injection.detailUserRepository(),
                    Injection.localUserRepository(context)
                )
            }.also { instance = it }
    }
}
