package com.dicoding.expert.ui.pages.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.model.User
import com.dicoding.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    private var useCase: UserUseCase = userUseCase

    fun getDetailUser(username: String) = useCase.getDetailUser(username).asLiveData()
    fun setFavorite(user: User) = useCase.setFavoriteUser(user).asLiveData()
    fun hasAdded(id: Int) = useCase.hasAdded(id).asLiveData()
    fun deletedFavUser(id: Int) =  useCase.deleteFavUser(id).asLiveData()
}