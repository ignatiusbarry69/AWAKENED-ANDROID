package com.example.storyapp.logic

import android.content.Context
import com.example.storyapp.logic.repository.db.StoryDatabase
import com.example.storyapp.logic.repository.preference.UserPreference
import com.example.storyapp.logic.repository.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryAppRepository {
        val apiService = ApiConfig.getApiService()
        val preference = UserPreference(context)
        val database = StoryDatabase.getDatabase(context)
        val appExecutors = com.example.storyapp.logic.utils.AppExecutors()

        return StoryAppRepository.getInstance(apiService, preference,appExecutors,database)
    }
}