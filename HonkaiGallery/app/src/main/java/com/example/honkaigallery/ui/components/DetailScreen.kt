package com.example.honkaigallery.ui.components

import com.example.honkaigallery.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.honkaigallery.Characters
import com.example.honkaigallery.Screen
import com.example.honkaigallery.ui.theme.Purple700

@Composable
fun DetailCharacter(
    navController: NavController,
    character: Characters,
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
                    IconButton(onClick = {navController.navigate(route = Screen.About.route) }) {
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
                .verticalScroll(rememberScrollState())
        )
        {
            Image(
                painter = painterResource(character.image),
                contentDescription = "Avatar",
                modifier = Modifier
                    .padding(15.dp)
                    .border(5.dp, Purple700, CircleShape)
                    .size(200.dp)
                    .clip(
                        CircleShape
                    )
                    .align(Alignment.CenterHorizontally),

                )
            Text(
                text = character.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(character.path),
                        contentDescription = "Path",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when(character.path){
                            R.drawable.path_the_erudition -> "Path of Erudition"
                            R.drawable.path_the_destruction -> "Path of Destruction"
                            R.drawable.path_the_harmony -> "Path of Harmonny"
                            R.drawable.path_the_nihility -> "Path of Nihility"
                            R.drawable.path_the_hunt -> "Path of Hunt"
                            R.drawable.path_the_preservation -> "Path of Preservation"
                            else -> "Path of Abundance"
                        }

                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(character.element),
                        contentDescription = "Element",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when(character.element){
                            R.drawable.fire_sm -> "Blessing of Fire"
                            R.drawable.lightning_sm -> "Blessing of Lightning"
                            R.drawable.ice_sm -> "Blessing of Ice"
                            R.drawable.wind_sm -> "Blessing of Wind"
                            R.drawable.physical_sm -> "Blessing of Physical"
                            R.drawable.quantum_sm -> "Blessing of Quantum"
                            else -> "Blessing of Imaginary"
                        }

                    )
                }
            }

        }

    }
}

