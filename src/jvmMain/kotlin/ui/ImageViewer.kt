package ui

import MainViewModel
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageViewer(
    vm: MainViewModel,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().weight(1f)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
        ){
            ImageComparison(
                originalImage = vm.image,
                changedImage = vm.enhancedImage,
                scale = vm.imageScale,
                modifier = Modifier.align(Alignment.Center)
            )
            if(isLoading){
                CircularProgressIndicator()
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.End)
        ) {
            Slider(
                value = vm.imageScale,
                valueRange = 0.15f..5f,
                onValueChange = { vm.imageScale = it},
                modifier = Modifier.width(320.dp)
            )
            Text(
                text = ((vm.imageScale * 100).toInt()).toString()+"%",
                modifier = Modifier.width(60.dp)
            )
        }
    }
}