package ui.explorerPart

import MainViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.imagePart.ImageViewer

@Composable
fun CatalogViewer(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ){
        Column(
            modifier = Modifier.fillMaxWidth(0.5f)
        ){
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier
            ){
                items(vm.explorer.folderList){folder ->
                    FolderCard(folder) {
                        vm.explorer.setCurrentPath(folder)
                        vm.coroutineScope.launch {
                            vm.loadImageQuality()
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val head = listOf("Имя файла", "Размер", "Разрешение", "BRISQUE")
                val weights = listOf(3f, 1f, 1f, 1f)
                head.forEachIndexed { index, text ->
                    Text(text = text, modifier = Modifier.weight(weights[index]))
                }
                Button(
                    onClick = {
                        vm.explorer.deleteFiles()
                    },
                    modifier = Modifier
                ){
                    Icon(
                        Icons.Default.Delete,
                        "Удалить выделенное",
                        modifier = Modifier
                    )
                }
            }
            LazyColumn {
                items(vm.explorer.fileList){
                    ImageCard(it, vm) {
                        vm.loadImage(it.filePath)
                    }
                }
            }
        }
        ImageViewer(
            vm = vm,
            scaleRange = 0.10f..3f,
            modifier = Modifier.fillMaxSize()
        )
    }
}