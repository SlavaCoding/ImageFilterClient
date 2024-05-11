package ui.imagePart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ui.imagePart.NullSafetyImage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageComparison(
    originalImage: ImageBitmap?,
    changedImage: ImageBitmap?,
    scale: Float = 1f,
    modifier: Modifier = Modifier
){
    val width = ((originalImage?.width ?: 0) * scale).dp
    val height = ((originalImage?.height ?: 0) * scale).dp
    Box(modifier){
        NullSafetyImage(
            image = originalImage,
            contentDescription = "Исходное изображение",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.width(width)
                .height(height)
        )
        var alpha by remember { mutableStateOf(1f) }
        NullSafetyImage(
            image = changedImage,
            contentDescription = "Обработанное изображение",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.alpha(alpha).width(width)
                .height(height)
                .onPointerEvent(PointerEventType.Press,
                    onEvent = {
                        alpha = 0f
                    })
                .onPointerEvent(PointerEventType.Release,
                    onEvent = {
                        alpha = 1f
                    })
        )
    }
}