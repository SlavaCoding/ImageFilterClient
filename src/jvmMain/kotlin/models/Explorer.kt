package models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class Explorer {
    var currDir by mutableStateOf(File(""))
    var fileList by mutableStateOf<MutableList<FileItem>>(mutableListOf())
    var folderList by mutableStateOf<MutableList<File>>(mutableListOf())
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
                            files.add(FileItem(it, sizeToString(it.length()), mutableStateOf(null)))
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

    suspend fun loadIcons() = coroutineScope{
        fileList.forEach{
            launch(Dispatchers.IO){
                try {
//                    if(it.icon == null){
//                        it.icon = loadImageBitmap(it.filePath)
//                    }

                } catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }
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