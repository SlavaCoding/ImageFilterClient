package ui.explorerPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderCard(
    item: File,
    onClick: () -> Unit
){
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = onClick,
        modifier = Modifier.height(35.dp).padding(5.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(Modifier.width(5.dp))
            Icon(Icons.Outlined.Folder, null)
            Spacer(Modifier.width(3.dp))
            Text(text = item.name, maxLines = 1)
        }
    }
}