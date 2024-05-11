package ui.explorerPart

import MainViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import models.FileItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    item: FileItem,
    vm: MainViewModel,
    onClick: () -> Unit
){
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.height(35.dp).padding(3.dp).fillMaxWidth(),
        onClick = onClick
    )
    {
        Row{
            Row {
                val info = listOf(
                    item.filePath.name,
                    item.size,
                    item.width.toString() + "x" + item.height.toString(),
                    String.format("%.3f", item.brisque.value)
                )
                val weights = listOf(3f, 1f, 1f, 1f)
                info.forEachIndexed { index, text ->
                    Text(
                        text = text,
                        modifier = Modifier.weight(weights[index])
                    )
                    Spacer(Modifier.width(3.dp))
                }
                var shifted by remember { mutableStateOf(false) }
                Checkbox(
                    checked = item.checked.value,
                    onCheckedChange = {
                        item.checked.value = it
                        if (it && shifted){
                            vm.explorer.selectUp(item)
                        }
                    },
                    modifier = Modifier.onKeyEvent {keyEvent ->
                        shifted = (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.ShiftLeft)
                        false
                    }
                )
            }
        }

    }
}