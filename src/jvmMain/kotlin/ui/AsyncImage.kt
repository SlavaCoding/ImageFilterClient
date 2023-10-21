package ui

import ViewModel
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import java.io.File

@Composable
fun AsyncImage(
    image: ImageBitmap?,
    contentDescription: String = "NoneDescription",
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    image?.let {
        Image(
            painter = BitmapPainter(it),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}