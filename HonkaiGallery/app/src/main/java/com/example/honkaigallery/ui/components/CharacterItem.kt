package com.example.honkaigallery.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.honkaigallery.Characters

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharactersItem(
    char: Characters,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card (
        modifier = modifier.width(100.dp).padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ){
        Column {
            Box {
                Image(
                    painter = painterResource(char.stars),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                )
                Image(
                    painter = painterResource(char.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(148.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Image(
                    painter = painterResource(char.element),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier

                        .height(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.TopStart)
                )
            }

            Column(modifier = Modifier
                .padding(8.dp)
                .align(CenterHorizontally)){
                Text(
                    text = char.name,
                    style = MaterialTheme.typography.subtitle2,

                )
            }
        }
    }
}

