package com.example.honkaigallery.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.honkaigallery.MainViewModel
import com.example.honkaigallery.Screen
import com.example.honkaigallery.ViewModelFactory
import com.example.honkaigallery.data.CharacterRepository

@Composable
fun HonkaiGalleryApp(
    viewModel: MainViewModel = viewModel(
        factory = ViewModelFactory(
            CharacterRepository()
        )
    ),
    navController: NavController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Menu")
                    }
                },
                title = {
                    Text(text = "Honkai Gallery")
                },
                actions = {
                    IconButton(onClick = { navController.navigate(route = Screen.About.route) }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "about_page")
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
        {
            val query by viewModel.query
            SearchBar(
                query = query,
                onQueryChange = viewModel::search,
            )
            CharactersGrid(viewModel, navController)
        }

    }

}

@Composable
fun CharactersGrid(
    viewModel: MainViewModel,
    navController: NavController
) {
    val listCharacters by viewModel.listCharacters.collectAsState()
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(8.dp),
        state = listState
    ) {
        items(listCharacters, key = { it -> it.name }) { item ->
            CharactersItem(item){
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "character",
                    value = item
                )
                navController.navigate(Screen.Detail.route)
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HonkaiGalleryApp(
        viewModel(
            factory = ViewModelFactory(
                CharacterRepository()
            )
        ),
        navController = rememberNavController()
    )
}