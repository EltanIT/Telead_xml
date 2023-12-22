package com.example.telead_xml.data.shared_pref

import android.content.Context
import android.content.SharedPreferences

class GetEmail {

    private lateinit var sharedPreferences: SharedPreferences

    fun execute(context: Context): String? {
        sharedPreferences = context.getSharedPreferences("email", Context.MODE_PRIVATE)

        return sharedPreferences.getString("email", "")
    }
}