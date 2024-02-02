package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.FileItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    item: FileItem,
    onClick: () -> Unit
){
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier.padding(7.dp),
        onClick = onClick
    )
    {
        Row{
            Box(
                modifier = Modifier.size(100.dp)
            ){
                item.icon?.let {
                    Image(
                        painter = BitmapPainter(it),
                        contentDescription = "icon"
                    )
                }
            }
            Column {
                Text(
                    text = item.filePath.name,
                    modifier = Modifier.width(150.dp)
                                        .height(25.dp)
                )
                Spacer(Modifier)
                Text(
                    text = item.size,
                    fontSize = 10.sp
                )
                Text(
                    text = item.brisque.value.toString()
                )
            }
        }

    }
}