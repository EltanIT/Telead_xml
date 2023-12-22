package com.example.telead_xml.data.repository.auth

import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.LoginData
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class TokenRepository {

    private val url = URLs().tokenUrl

    fun request(data: String): String?{
        val client = OkHttpClient()

        val bodyMap = mapOf(
            "value" to data)
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
                return response.body?.string()
            }
        }catch (e: IOException){
            return null
        }
    }
}