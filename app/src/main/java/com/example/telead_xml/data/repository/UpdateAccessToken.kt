package com.example.telead_xml.data.repository

import android.content.Context
import com.example.telead_xml.data.repository.auth.TokenRepository
import com.example.telead_xml.data.shared_pref.GetRefreshToken
import com.example.telead_xml.data.shared_pref.SaveRefreshToken
import org.json.JSONObject

class UpdateAccessToken {

    fun execute(context: Context): Boolean{
        try {
            val body = TokenRepository().request(GetRefreshToken().execute(context))
            if (body!=null){
                val jsonObject = JSONObject(body)
                val refreshToken = jsonObject.getString("refreshToken")
                SaveRefreshToken().execute(refreshToken, context)
                return true
            }
        }catch (e: Exception){
            return false
        }
        return false

    }
}