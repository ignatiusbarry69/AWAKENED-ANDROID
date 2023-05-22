package com.example.honkaigallery.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.honkaigallery.Screen

@Composable
fun AboutScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(route = Screen.Home.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Menu")
                    }
                },
                title = {
                    Text(text = "Honkai Gallery")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "about_page"
                        )
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
            Image(
                painter = painterResource(com.example.honkaigallery.R.drawable.about),
                contentDescription = "Avatar",
                modifier = Modifier.padding(15.dp).border(5.dp, com.example.honkaigallery.ui.theme.Purple700, CircleShape).size(200.dp).clip(CircleShape).align(Alignment.CenterHorizontally),

            )
            Text(
                text = "Ignatius Barry Santoso",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 22.sp
            )
            Text(
                text = "ignatius.barry@ti.ukdw.ac.id",
                modifier = Modifier.padding(15.dp).align(Alignment.CenterHorizontally),
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        }

    }
}

