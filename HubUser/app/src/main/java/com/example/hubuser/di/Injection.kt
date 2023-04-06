package com.example.hubuser.di

import android.content.Context
import com.example.hubuser.database.FavoriteUserDatabase
import com.example.hubuser.repository.GithubUserRepository
import com.example.hubuser.retrofit.ApiConfig
import com.example.hubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return GithubUserRepository.getInstance(apiService, dao, appExecutors)
    }
}