package ui

import ViewModel
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import enums.Page
import java.io.File

@Composable
@Preview
fun mainForm() {
    val vm by remember { mutableStateOf(ViewModel()) }
    val topAppBarHeight = 55.dp
    Surface(modifier = Modifier.fillMaxSize()){
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.height(topAppBarHeight),
                    title = {
                        Text(vm.filePath.path)
                    },
                    actions = {
                        Button(
                            onClick = {}
                        ){
                            Text("Применить фильтр")
                        }
                        Button(
                            onClick = { vm.showFilePicker = true })
                        {
                            Text("Открыть")
                        }
                    })
            }
        ) {
            val items = Page.values()

            Row(modifier = Modifier.fillMaxSize()) {
                NavigationRail (
                    modifier = Modifier.padding(top = topAppBarHeight)
                ){
                    items.forEach{ item ->
                        NavigationRailItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = vm.selectedPage == item,
                            onClick = { vm.selectedPage = item },
                            alwaysShowLabel = false
                        )
                    }
                }
                when(vm.selectedPage){
                    Page.HOME -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize().padding(top = topAppBarHeight)
                        ){
                            AsyncImage(image = vm.image)
                        }
                    }
                    Page.CATALOGS -> {
                        Box(
                            modifier = Modifier.padding(top = topAppBarHeight)
                        ){
                            Text("Здесь будет просмотр файлов")
                        }
                    }
                }
            }
        }
        FilePicker(vm.showFilePicker, fileExtensions = listOf("jpg", "png")) { file ->
            vm.showFilePicker = false
            file?.let {
                vm.loadImage(File(it.path))
            }
        }
    }
}