package com.example.honkaigallery.data

import com.example.honkaigallery.Characters
import com.example.honkaigallery.dummyChar

class CharacterRepository {
    fun getCharacters():List<Characters>{
        return dummyChar
    }

    fun searchCharacter(query: String): List<Characters>{
        return dummyChar.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}