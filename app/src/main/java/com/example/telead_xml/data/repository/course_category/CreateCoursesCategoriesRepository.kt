package com.example.telead_xml.data.repository.course_category

import android.util.Log
import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URL

class CreateCoursesCategoriesRepository {

    private val url = URLs().createCourseCategoriesUrl

    fun request(token: String): ResponseData?{
        val client = OkHttpClient()

        val bodyMap = mapOf(
            "name" to "3д Дизайн")
        val gson = Gson()

        val body = RequestBody.create("application/json".toMediaTypeOrNull(),gson.toJson(bodyMap))
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", token)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                Log.i("swagger", response.code.toString()+" "+response.request)
                return ResponseData(response, response.body?.string())
            }
        }catch (e: Exception){
            return null
        }
    }
}