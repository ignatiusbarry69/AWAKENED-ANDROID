package com.example.hubuser.background

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hubuser.di.Injection
import com.example.hubuser.repository.GithubUserRepository


class FactoryRepository private constructor(private val favoriteRepository: GithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FactoryRepository? = null
        fun getInstance(context: Context): FactoryRepository =
            instance ?: synchronized(this) {
                instance ?: FactoryRepository(Injection.provideRepository(context))
            }.also { instance = it }
    }
}