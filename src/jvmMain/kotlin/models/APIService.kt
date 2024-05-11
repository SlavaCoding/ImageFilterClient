package models

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface APIService {
    @Multipart
    @POST("/upload/")
    suspend fun uploadImage(@Part file: MultipartBody.Part, @Query("width") width: Int, @Query("height") height: Int, @Query("model_name") modelName: String):Response<ResponseBody>

    @GET("/brisque/")
    suspend fun brisqueTest(@Query("path") path: String): Double
}

fun getService(timeOut: Long): APIService {
    val httpClient = OkHttpClient.Builder()
        .connectTimeout(timeOut, TimeUnit.SECONDS)
        .readTimeout(timeOut, TimeUnit.SECONDS)
        .writeTimeout(timeOut, TimeUnit.SECONDS)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:8000")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(APIService::class.java)
}