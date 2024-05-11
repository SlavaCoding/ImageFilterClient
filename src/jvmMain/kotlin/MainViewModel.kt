import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.res.loadImageBitmap
import enums.Page
import kotlinx.coroutines.CoroutineScope
import models.Explorer
import models.getService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.imageio.ImageIO

class MainViewModel{
    lateinit var coroutineScope : CoroutineScope
    val snackbarHostState = SnackbarHostState()
    var filePath by mutableStateOf(File(""))
    var image by mutableStateOf<ImageBitmap?>(null)
    var enhancedImage by mutableStateOf<ImageBitmap?>(null)
    var imgLoadProcess by mutableStateOf(false)
    var showFilePicker by mutableStateOf(false)
    var selectedPage by mutableStateOf(Page.HOME)
    val explorer by mutableStateOf(Explorer())

    var modelName by mutableStateOf("denoise_10")
    var toEnhansed by mutableStateOf(true)

    fun loadImageQuality(){
        val service = getService(300)
        coroutineScope.launch {
            explorer.fileList.forEach {fileItem ->
                if (fileItem.brisque.value == null){
                    fileItem.brisque.value = service.brisqueTest(fileItem.filePath.path)
                }
            }
            explorer.fileList = explorer.fileList.sortedByDescending { it.brisque.value }
        }
    }

    private suspend fun sendImage(path: File): Response<ResponseBody> {
        val service = getService(1000)
        val filePart = MultipartBody.Part.createFormData(
            "file",
            filePath.name,
            RequestBody.create(MediaType.parse("image/*"), path)
        )
        return service.uploadImage(filePart, image?.width ?: 0, image?.height ?: 0, modelName)
    }

    fun enhanceImage() {
        coroutineScope.launch {
            try{
                imgLoadProcess = true
                val response: Response<ResponseBody>
                if (toEnhansed && enhancedImage != null){
                    val path = File("tmp.png")
                    val outputStream = FileOutputStream(path)
                    ImageIO.write(enhancedImage?.toAwtImage(), "png", outputStream)
                    outputStream.close()
                    response = sendImage(path)
                    path.delete()
                }
                else {
                    response = sendImage(filePath)
                }
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { encodedImage ->
                        enhancedImage = encodedImage.byteStream().buffered().use { loadImageBitmap(it) }
                    }
                } else {
                    println("Error " + response.code() + ": " + response.message())
                }
                imgLoadProcess = false
            }
            catch (e: Exception){
                println(e.message)
                imgLoadProcess = false
            }
        }

    }
    fun loadImage(file: File){
        filePath = file
        coroutineScope.launch {
            explorer.setCurrentPath(filePath.parentFile)
            launch(Dispatchers.IO) {
                try {
                    image = loadImageBitmap(filePath)
                    enhancedImage = null
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun saveImage(){
        coroutineScope.launch {
            launch(Dispatchers.IO) {
                enhancedImage?.let {
                    val path = filePath.path
                    val endOfName = path.lastIndexOf(".")
                    var i = 1
                    while (true){
                        val copyFileName = File(path.substring(0, endOfName) + "(" + i + ")" + ".png")
                        if (!copyFileName.exists()){
                            try {
                                val outputStream = FileOutputStream(copyFileName)
                                ImageIO.write(it.toAwtImage(), "png", outputStream)
                                outputStream.close()
                                snackbarHostState.showSnackbar("Файл сохранен в " + copyFileName.path, withDismissAction = true)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            } finally {
                                break
                            }
                        }
                        i++
                    }
                }
            }
        }
    }

}
fun loadImageBitmap(file: File): ImageBitmap = file.inputStream().buffered().use { loadImageBitmap(it) }

/////////////////////////
//                call.enqueue(object: Callback<NumberResponse> {
//                    override fun onResponse(call: Call<NumberResponse>, response: Response<NumberResponse>) {
//                        if (response.isSuccessful) {
//                            val result: Double? = response.body()?.value?.toDouble()
//                            fileItem.brisque = result ?: 0.0
//                        } else {
//                            // Обработка ошибки
//                        }
//                    }
//                    override fun onFailure(call: Call<NumberResponse>, t: Throwable) {
//                        // Обработка ошибки
//                    }
//                })
//                        image?.let { original ->
//                            enhancedImage?.let { enhanced ->
//                                if (original.width != enhanced.width){
//                                    val img = enhanced.toAwtImage()
//                                    val at = AffineTransform()
//                                    at.translate(img.height.toDouble(), 0.0)
//                                    at.rotate(Math.toRadians(90.0))
//                                    val newImg = BufferedImage(img.height, img.width, BufferedImage.TYPE_INT_RGB)
//                                    val g = newImg.createGraphics()
//                                    g.drawImage(img, at, null)
//                                    g.dispose()
//                                    enhancedImage = newImg.toComposeImageBitmap()
//                                }
//                            }
//                        }