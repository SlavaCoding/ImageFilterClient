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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainViewModel{
    lateinit var coroutineScope : CoroutineScope
    var filePath by mutableStateOf(File(""))
    var image by mutableStateOf<ImageBitmap?>(null)
    var enhancedImage by mutableStateOf<ImageBitmap?>(null)
    var imageScale by mutableStateOf(1.0f)
    var imgLoadProcess by mutableStateOf(false)
    var showFilePicker by mutableStateOf(false)
    var selectedPage by mutableStateOf(Page.HOME)
    val explorer by mutableStateOf(Explorer())

    fun loadImageQuality(){
        val service = getService(100)
        coroutineScope.launch {
            explorer.fileList.forEach {fileItem ->
                if (fileItem.brisque.value == null){
                    fileItem.brisque.value = service.brisqueTest(fileItem.filePath.path)
                }
            }
            selectedPage = Page.HOME
        }
    }

    private suspend fun sendImage(): Response<ResponseBody> {
        val service = getService(500)
        val filePart = MultipartBody.Part.createFormData(
            "file",
            filePath.name,
            RequestBody.create(MediaType.parse("image/*"), filePath)
        )
        return service.uploadImage(filePart, image?.width ?: 0, image?.height ?: 0)
    }

    fun enhanceImage() {
        try{
            coroutineScope.launch {
                imgLoadProcess = true
                val response = sendImage()
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
        }
        catch (e: Exception){
            println(e.message)
            imgLoadProcess = false
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