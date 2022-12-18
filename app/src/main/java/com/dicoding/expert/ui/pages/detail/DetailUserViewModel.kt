package com.dicoding.expert.ui.pages.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.expert.domain.entities.User
import com.dicoding.expert.domain.usecases.UserUseCase

class DetailUserViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getDetailUser(username: String) = userUseCase.getDetailUser(username).asLiveData()
    fun setFavorite(user:User) = userUseCase.setFavoriteUser(user).asLiveData()
    fun hasAdded(id: Int) = userUseCase.hasAdded(id).asLiveData()
    fun deletedFavUser(id: Int) =  userUseCase.deleteFavUser(id).asLiveData()
}