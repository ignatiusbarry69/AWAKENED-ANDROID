package com.example.storyapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.logic.Result
import com.example.storyapp.logic.StoryAppRepository
import com.example.storyapp.logic.repository.preference.UserModel
import com.example.storyapp.logic.repository.retrofit.responses.ListStoryItem

class MapsViewModel(private val storyRepository: StoryAppRepository): ViewModel(){
    fun getUser(): LiveData<UserModel> {
        val _userModel = MutableLiveData<UserModel>()
        val userModel: LiveData<UserModel> = _userModel
        _userModel.value = storyRepository.getPref().getUser()
        return userModel
    }

    fun getLocation(token: String, location: Int? = 0): LiveData<Result<List<ListStoryItem>>> =
        storyRepository.getLocation(token, location)
}