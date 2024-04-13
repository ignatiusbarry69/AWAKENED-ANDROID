package com.example.storyapp.logic.repository.preference

import android.content.Context
import android.preference.PreferenceManager

class UserPreference(context: Context) {
    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val TOKEN = "token"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val ISLOGIN = "false"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserModel){
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(TOKEN, value.token)
        editor.putString(EMAIL, value.email)
        editor.putString(PASSWORD, value.password)
        editor.putBoolean(ISLOGIN, value.isLogin)
        editor.apply()
    }

    fun setFirst(){
        val editor = preferences.edit()
        editor.putString(NAME, "kek")
        editor.apply()
    }

    fun logout(){
        setUser(UserModel())
    }

    fun getUser(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.token = preferences.getString(TOKEN, "")
        model.email = preferences.getString(EMAIL, "")
        model.password = preferences.getString(NAME, "")
        model.isLogin = preferences.getBoolean(ISLOGIN, false)
        return model
    }
}
