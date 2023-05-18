package com.example.storyapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.logic.StoryAppRepository
import com.example.storyapp.logic.repository.preference.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryAppRepository) : ViewModel() {
    fun upload2(token: String, desc:RequestBody,file:MultipartBody.Part, lat: RequestBody? = null, lon: RequestBody?=null) = storyRepository.uploadStory2(token,desc,file, lat, lon)

    fun getUser(): LiveData<UserModel> {
        val _userModel = MutableLiveData<UserModel>()
        val userModel: LiveData<UserModel> = _userModel

        _userModel.postValue(storyRepository.getPref().getUser())
        return userModel
    }
}

