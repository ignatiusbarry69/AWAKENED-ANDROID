package com.example.honkaigallery

sealed class Screen(val route:String) {
    object Home: Screen(route = "home_screen")
    object Detail: Screen(route = "detail_screen")
    object About: Screen(route = "about_screen")
}