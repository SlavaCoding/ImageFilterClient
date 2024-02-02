package ui

import MainViewModel
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import enums.Page
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun mainForm() {
    val vm by remember { mutableStateOf(MainViewModel()) }
    vm.coroutineScope = rememberCoroutineScope()
    val topAppBarHeight = 55.dp
    Surface(modifier = Modifier.fillMaxSize()){
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.height(topAppBarHeight),
                    title = {
                        when(vm.selectedPage){
                            Page.HOME -> Text(vm.filePath.path)
                            Page.CATALOGS -> {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Card(
                                        elevation = CardDefaults.cardElevation(5.dp),
                                        onClick = {
                                            vm.explorer.toParentFolder()
                                            vm.coroutineScope.launch {
                                                vm.loadImageQuality()
                                            }
                                        }
                                    ){
                                        Icon(Icons.Outlined.ArrowUpward, null)
                                    }
                                    Text(vm.explorer.currDir.path)
                                }
                            }
                        }
                    },
                    actions = {
                        Button(
                            onClick = { vm.showFilePicker = true })
                        {
                            Text("Открыть")
                        }
                        Button(
                            onClick = { vm.enhanceImage() })
                        {
                            Text("Улучшить")
                        }
                    })
            }
        ) {
            val items = Page.entries.toTypedArray()

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
                        ImageViewer(
                            vm = vm,
                            isLoading = vm.imgLoadProcess,
                            modifier = Modifier.fillMaxSize().padding(top = topAppBarHeight)
                        )
                    }
                    Page.CATALOGS -> {
                        CatalogViewer(
                            vm = vm,
                            modifier = Modifier.padding(top = topAppBarHeight)
                        )
                        vm.coroutineScope.launch{
                            vm.loadImageQuality()
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