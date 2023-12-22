package com.example.telead_xml.data.repository.course

import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.FilterData
import com.example.telead_xml.domen.objects.RegistrationData
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.net.URL

class GetCourseBenefitsRepository {

    private val url = URLs().getCourseBenefitsUrl

    fun request(data: String): ResponseData?{
        val client = OkHttpClient()

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