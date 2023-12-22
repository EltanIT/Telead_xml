package com.example.telead_xml.data.repository.course

import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class CreateCourseRepository {

    private val url = URLs().getCourseForFilterUrl

    fun request(data: CoursesData): ResponseData?{
        val client = OkHttpClient()

        val bodyMap = mapOf(
            "name" to data.name,
            "description" to data.description,
            "price" to data.price,
            "" to data,//
            "costType" to data.costType,
            "formatType" to data.formatType,
            "levelType" to data.difficultLevelType,
            "courseBenefits" to data.benefits)
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