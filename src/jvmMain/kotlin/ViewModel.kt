import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.res.loadImageBitmap
import enums.Page
import java.io.File
import java.io.IOException

class ViewModel {
    var filePath by mutableStateOf(File(""))
    var image by mutableStateOf<ImageBitmap?>(null)
    var showFilePicker by mutableStateOf(false)
    var selectedPage by mutableStateOf(Page.HOME)

    fun loadImage(file: File){
        filePath = file
        GlobalScope.launch(Dispatchers.IO){
            try {
                image = loadImageBitmap(filePath)
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
}

fun loadImageBitmap(file: File): ImageBitmap = file.inputStream().buffered().use { loadImageBitmap(it) }