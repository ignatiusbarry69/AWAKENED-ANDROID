package com.example.storyapp.ui.signup

import androidx.lifecycle.ViewModel
import com.example.storyapp.logic.StoryAppRepository

class SignupViewModel (private val storyRepository: StoryAppRepository):ViewModel(){
    fun regis2(name: String, email: String, pass:String) = storyRepository.register(name,email,pass)
}