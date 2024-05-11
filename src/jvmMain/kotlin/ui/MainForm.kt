package ui

import MainViewModel
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import enums.Page
import kotlinx.coroutines.launch
import ui.explorerPart.CatalogViewer
import ui.imagePart.ImageViewer
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
            snackbarHost = { SnackbarHost(vm.snackbarHostState) },
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
                        Tooltip("Выбор нейронной сети"){
                            ModelSelector(
                                vm = vm,
                                modifier = Modifier.width(150.dp)
                            )
                        }
                        Tooltip("Если флаг установлен, то нейросеть будет обрабатывать уже улучшенное изображение, а не оригинал") {
                            Row{
                                Checkbox(
                                    checked = vm.toEnhansed,
                                    onCheckedChange = { vm.toEnhansed = it },
                                )
                                Text("Применять\nк копии")
                                Spacer(Modifier.width(5.dp))
                            }
                        }
                        Button(
                            onClick = { vm.showFilePicker = true })
                        {
                            Text("Открыть")
                        }
                        Tooltip("Применение выбранной нейросети к изображению. " +
                                "Нажмите на обработанное изображение, чтобы сравнить с оригиналом"){
                            Button(
                                onClick = { vm.enhanceImage() })
                            {
                                Text("Улучшить")
                            }
                        }
                        Tooltip("Изображение сохраняется в формате png в папке с оригинальным изображением. \n" +
                                "К имени файла добавляется порядковый номер сохраняемой копии"){
                            Button(
                                onClick = { vm.saveImage() })
                            {
                                Text("Сохранить")
                            }
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
                            scaleRange = 0.15f..5f,
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