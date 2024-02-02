package enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class Page(val title: String, val icon: ImageVector){
    HOME("Просмотр", Icons.Filled.Home),
    CATALOGS("Каталог", Icons.Filled.Folder)
}