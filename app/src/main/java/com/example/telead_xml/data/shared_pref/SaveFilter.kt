package com.example.telead_xml.data.shared_pref

import android.content.Context
import android.content.SharedPreferences

class SaveFilter {

    private lateinit var sharedPreferences: SharedPreferences

    fun execute(data: String, context: Context){
        sharedPreferences = context.getSharedPreferences("filter", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.remove("filter")
        editor.putString("filter", data)
        editor.apply()
    }
}