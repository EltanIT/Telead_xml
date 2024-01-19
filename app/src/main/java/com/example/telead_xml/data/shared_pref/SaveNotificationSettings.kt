package com.example.telead_xml.data.shared_pref

import android.content.Context
import android.content.SharedPreferences

class SaveNotificationSettings {

    private lateinit var sharedPreferences: SharedPreferences

    fun execute(data: String, context: Context){
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.remove("notifications")
        editor.putString("notifications", data)
        editor.apply()
    }
}