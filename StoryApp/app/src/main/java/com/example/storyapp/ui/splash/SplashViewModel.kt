package com.example.storyapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.logic.StoryAppRepository
import com.example.storyapp.logic.repository.preference.UserModel

class SplashViewModel (private val storyRepository: StoryAppRepository) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        val _userModel = MutableLiveData<UserModel>()
        val userModel: LiveData<UserModel> = _userModel
        _userModel.value = storyRepository.getPref().getUser()
        return userModel
    }
}