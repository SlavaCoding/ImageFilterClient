package ui

import androidx.compose.runtime.Composable
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme
import java.io.File

@Composable
@Preview
fun mainForm(){
    Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
        SmallTopAppBar(
            title = {
                Text("Верхняя панель")
            },
            actions = {
                Button({}){
                    Text("Открыть")
                }
            })
        AsyncImage(
            load = {loadImageBitmap(File("B:\\фото с тел\\bluetooth\\1625755330544.jpg"))},
            painterFor = { remember { BitmapPainter(it) }},
            contentDescription = "Loaded image"
        )

    }

}