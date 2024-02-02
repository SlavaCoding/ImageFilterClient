package ui

import MainViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import enums.Page
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogViewer(
    vm: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp)
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
        Text("Здесь будет просмотр файлов")
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 250.dp)
        ) {
            items(vm.explorer.fileList){
                ImageCard(it){
                    vm.loadImage(it.filePath)
                    vm.selectedPage = Page.HOME
                }
            }
        }
    }
}