package com.example.honkaigallery

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.honkaigallery.data.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(private val repository: CharacterRepository) : ViewModel() {
    val listCharacters = MutableStateFlow(repository.getCharacters())

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        listCharacters.value = repository.searchCharacter(_query.value)
    }
}


//we can actually use hilt instead of factory
class ViewModelFactory(private val repository: CharacterRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}