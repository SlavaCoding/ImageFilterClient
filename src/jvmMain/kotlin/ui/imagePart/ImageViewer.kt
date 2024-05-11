package ui.imagePart

import MainViewModel
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageViewer(
    vm: MainViewModel,
    scaleRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
){
    var imageScale by remember { mutableStateOf(1f) }
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
                scale = imageScale,
                modifier = Modifier.align(Alignment.Center)
            )
            if(vm.imgLoadProcess){
                CircularProgressIndicator()
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.End)
        ) {
            Slider(
                value = imageScale,
                valueRange = scaleRange,
                onValueChange = { imageScale = it},
                modifier = Modifier.width(320.dp)
            )
            Text(
                text = ((imageScale * 100).toInt()).toString()+"%",
                modifier = Modifier.width(60.dp)
            )
        }
    }
}