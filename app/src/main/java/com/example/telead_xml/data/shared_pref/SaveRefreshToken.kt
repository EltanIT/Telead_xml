package com.example.telead_xml.data.shared_pref

import android.content.Context
import android.content.SharedPreferences

class SaveRefreshToken {

    private lateinit var sharedPreferences: SharedPreferences

    fun execute(data: String, context: Context){
        sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.remove("refreshToken")
        editor.putString("refreshToken", data)
        editor.apply()
    }
}