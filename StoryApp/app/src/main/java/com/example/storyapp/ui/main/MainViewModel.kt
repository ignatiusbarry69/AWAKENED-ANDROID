package com.example.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.logic.StoryAppRepository
import com.example.storyapp.logic.repository.retrofit.responses.ListStoryItem


class MainViewModel(private val storyRepository: StoryAppRepository) : ViewModel() {
    fun logout(){
            storyRepository.logout()
    }

    val getListStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getListStory().cachedIn(viewModelScope)
}