package com.example.telead_xml.data.shared_pref

import android.content.Context
import android.content.SharedPreferences

class SaveEmail {

    private lateinit var sharedPreferences: SharedPreferences

    fun execute(data: String, context: Context){
        sharedPreferences = context.getSharedPreferences("email", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.putString("email", data)
        editor.apply()
    }
}