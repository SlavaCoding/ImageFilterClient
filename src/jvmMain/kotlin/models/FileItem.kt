package models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.io.File

data class FileItem(
    val filePath: File,
    val size: String,
    val width: Int,
    val height: Int,
    val brisque: MutableState<Double?> = mutableStateOf(null),
    val checked: MutableState<Boolean> = mutableStateOf(false)
)
