package com.example.hubuser.background

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hubuser.database.FavoriteUser
import com.example.hubuser.repository.GithubUserRepository

class FavoriteViewModel(private val favoriteRepository: GithubUserRepository) : ViewModel()  {
    fun isFavoriteUsers(username: String): LiveData<Boolean> = favoriteRepository.isFavoriteUsers(username)
    fun deleteFavoriteUser(user: FavoriteUser) {
        favoriteRepository.deleteFavoriteUser(user)
    }
    fun insertFavoriteUser(user: FavoriteUser) {
        favoriteRepository.insertFavoriteUser(user)
    }
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = favoriteRepository.getFavoriteUsers()

}