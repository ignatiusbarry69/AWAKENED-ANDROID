package com.example.storyapp.logic.repository.preference

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var name: String? =null,
    var token: String? =null,
    var email: String? = null,
    var password: String? = null,
    var isLogin: Boolean = false,
) : Parcelable