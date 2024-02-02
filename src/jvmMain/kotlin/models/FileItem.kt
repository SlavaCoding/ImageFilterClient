package models

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import java.io.File

data class FileItem(
    var filePath: File,
    val size: String,
    var brisque: MutableState<Double?>,
    var icon: ImageBitmap? = null
)
