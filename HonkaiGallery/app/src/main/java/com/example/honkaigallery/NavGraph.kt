package com.example.honkaigallery

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.honkaigallery.ui.components.AboutScreen
import com.example.honkaigallery.ui.components.DetailCharacter
import com.example.honkaigallery.ui.components.HonkaiGalleryApp

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController= navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            HonkaiGalleryApp(navController = navController)
        }
        composable(
            route = Screen.Detail.route
        ){
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<Characters>("character")
            result?.let { character ->
                DetailCharacter(navController = navController, character = character)
            }
        }
        composable(
            route = Screen.About.route
        ){
            AboutScreen(navController = navController)
        }
    }
}