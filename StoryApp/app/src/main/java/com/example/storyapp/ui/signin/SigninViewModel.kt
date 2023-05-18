package com.example.storyapp.ui.signin

import androidx.lifecycle.ViewModel
import com.example.storyapp.logic.StoryAppRepository

class SigninViewModel(private val storyRepository: StoryAppRepository): ViewModel(){
    fun login(email: String, pass: String) = storyRepository.loginCheck(email,pass)
}