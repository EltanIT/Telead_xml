package com.example.telead_xml.data.repository.course

import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.ResponseData
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL

class GetCourseByIdRepository {

    private val string = URLs().getCourseForId

    fun request(data: String): ResponseData?{
        val client = OkHttpClient()
        val url = URL(string+data)

        val request = Request.Builder()
            .url(url)
            .get()
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