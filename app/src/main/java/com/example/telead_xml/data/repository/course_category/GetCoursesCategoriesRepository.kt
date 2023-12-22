package com.example.telead_xml.data.repository.course_category

import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.FilterData
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class GetCoursesCategoriesRepository {

    private val url = URLs().getCourseCategoriesUrl

    fun request(): ResponseData?{
        val client = OkHttpClient()

        val bodyMap = mapOf(
            "count" to 2147483647,
            "loadPosition" to 2147483647)
        val gson = Gson()

        val body = RequestBody.create("application/json".toMediaTypeOrNull(),gson.toJson(bodyMap))
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                return ResponseData(response, response.body?.string())
            }
        }catch (e: IOException){
            return null
        }
    }
}