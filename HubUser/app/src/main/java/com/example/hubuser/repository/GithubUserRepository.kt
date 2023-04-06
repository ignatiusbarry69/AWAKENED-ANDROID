package com.example.hubuser.repository

import androidx.lifecycle.LiveData
import com.example.hubuser.database.FavoriteUser
import com.example.hubuser.database.FavoriteUserDao
import com.example.hubuser.retrofit.ApiService
import com.example.hubuser.utils.AppExecutors

class GithubUserRepository private constructor(
    private val apiService: ApiService,//i'll use this for experimental later to make the data flow properly and cleaner code
    private val FavoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
) {
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = FavoriteUserDao.getFavoriteUsers()

    fun isFavoriteUsers(username: String): LiveData<Boolean> = FavoriteUserDao.isFavorite(username)

    fun insertFavoriteUser(user: FavoriteUser) {
        appExecutors.diskIO.execute {
            FavoriteUserDao.insert(user)
        }
    }

    fun deleteFavoriteUser(user: FavoriteUser) {
        appExecutors.diskIO.execute {
            FavoriteUserDao.delete(user)
        }
    }

    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, favoriteUserDao, appExecutors)
            }.also { instance = it }
    }
}