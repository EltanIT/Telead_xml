package com.example.telead_xml.data.repository.course

import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.FilterData
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class GetCoursesForFiltersRepository {

    private val url = URLs().getCourseForFilterUrl

    fun request(data: FilterData, token: String): ResponseData?{
        val client = OkHttpClient()

        val bodyMap = mapOf(
            "costTypes" to data.costTypes,
            "formatTypes" to data.formatTypes,
            "difficultLevels" to data.difficultLevels,
            "categoryIds" to data.categoryIds,
            "minimumRating" to data.minimumRating,
            "count" to data.count,
            "countSkipped" to data.countSkipped)
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
                return ResponseData(response, response.body?.string())
            }
        }catch (e: IOException){
            return null
        }
    }
}