package com.example.telead_xml.data.repository.profile

import android.util.Log
import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.ProfileData
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

class PutProfileRepository {

    private val url = URLs().putProfileUrl

    fun request(data: ProfileData, token: String): ResponseData?{
        val client = OkHttpClient()

        val bodyMap = mapOf(
            "fullName" to data.fullName,
            "nickName" to data.nickname,
            "dob" to data.dob,
            "phone" to data.phone,
            "gender" to data.gender)
        val gson = Gson()

        val body = RequestBody.create("application/json".toMediaTypeOrNull(),gson.toJson(bodyMap))
        val request = Request.Builder()
            .url(url)
            .put(body)
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", token)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                Log.i("swagger", response.code.toString()+" "+response.request)
                return ResponseData(response, response.body?.string())
            }
        }catch (e: IOException){
            return null
        }
    }
}