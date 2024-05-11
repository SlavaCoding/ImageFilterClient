package models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.drew.imaging.ImageMetadataReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Explorer {
    var currDir by mutableStateOf(File(""))
    var fileList by mutableStateOf<List<FileItem>>(listOf())
    var folderList by mutableStateOf<List<File>>(listOf())
    init{
        setCurrentPath(File("C:\\"))
    }
    fun setCurrentPath(currentDir: File) {
        if(!currDir.path.equals(currentDir.path)){
            currDir = currentDir
            val files = mutableListOf<FileItem>()
            val folders = mutableListOf<File>()
            currDir.listFiles()?.forEach {
                if (it.isFile) {
                    val fileName = it.name
                    val dotIndex = fileName.lastIndexOf(".")
                    if (dotIndex>0){
                        if(fileName.substring(dotIndex+1)=="jpg"){
                            val dimensions = getImageDimensions(it)
                            dimensions?.let { dim ->
                                val (width, height) = dim
                                files.add(FileItem(it, sizeToString(it.length()), width, height))
                            }
                        }
                    }
                } else if (it.isDirectory) {
                    folders.add(it)
                }
            }
            fileList = files
            folderList = folders
        }
    }

    fun toParentFolder(){
        currDir.parentFile?.let {
            setCurrentPath(it)
        }
    }

    fun selectUp(selected: FileItem){
        val index = fileList.indexOf(selected) - 1
        for(i in index downTo 0){
            if (!fileList[i].checked.value){
                fileList[i].checked.value = true
            }
            else break
        }
    }

    fun getImageDimensions(file: File): Pair<Int, Int>? {
        try {
            val metadata = ImageMetadataReader.readMetadata(file)
            val directory = metadata.getFirstDirectoryOfType(com.drew.metadata.jpeg.JpegDirectory::class.java)
            val width = directory?.getInt(com.drew.metadata.jpeg.JpegDirectory.TAG_IMAGE_WIDTH)
            val height = directory?.getInt(com.drew.metadata.jpeg.JpegDirectory.TAG_IMAGE_HEIGHT)
            if (width != null && height != null){
                return Pair(width, height)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun deleteFiles(){
        val notDeleted = mutableListOf<FileItem>()
        fileList.forEach{
            if (it.checked.value){
                Files.deleteIfExists(Paths.get(it.filePath.path))
            }
            else {
                notDeleted.add(it)
            }
        }
        fileList = notDeleted
    }

    fun sizeToString(size : Long) : String{
        val kilobyte = 1024;
        val megabyte = kilobyte * 1024;
        return when{
            size < kilobyte -> "$size Б"
            size < megabyte -> String.format("%.2f КБ", size.toFloat()/kilobyte)
            else -> String.format("%.2f МБ", size.toFloat()/megabyte)
        }
    }
}