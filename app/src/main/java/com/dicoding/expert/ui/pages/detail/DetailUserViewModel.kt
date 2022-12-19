package com.dicoding.expert.ui.pages.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.expert.domain.entities.User
import com.dicoding.expert.domain.usecases.UserUseCase
import javax.inject.Inject

class DetailUserViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    private var useCase: UserUseCase = userUseCase

    fun getDetailUser(username: String) = useCase.getDetailUser(username).asLiveData()
    fun setFavorite(user:User) = useCase.setFavoriteUser(user).asLiveData()
    fun hasAdded(id: Int) = useCase.hasAdded(id).asLiveData()
    fun deletedFavUser(id: Int) =  useCase.deleteFavUser(id).asLiveData()
}