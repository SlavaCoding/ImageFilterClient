package ui

import MainViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModelSelector(
    vm: MainViewModel,
    modifier: Modifier = Modifier
)
{
    var expanded by remember { mutableStateOf(false) }
    val models = listOf("denoise_10", "denoise_20", "resize", "resize_4")
    val icon = if (expanded){
        Icons.Filled.KeyboardArrowUp
    } else Icons.Filled.KeyboardArrowDown
    Column {
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.border(1.dp, Color.Gray)
                .onClick {
                    expanded = true
                }
        ){
            Text(vm.modelName, Modifier.padding(5.dp))
            Icon(icon, "Arrow")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false},
            modifier = modifier
        ){
            models.forEach{ label ->
                DropdownMenuItem(text = { Text(label) },
                    onClick = {
                        vm.modelName = label
                        expanded = false
                    }
                )
            }
        }
    }
}