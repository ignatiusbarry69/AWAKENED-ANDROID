package com.example.storyapp.logic.repository.retrofit.responses

import com.google.gson.annotations.SerializedName

data class PushResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String? = null
)