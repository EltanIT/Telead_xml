package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class LessonData(
    @SerializedName("id"       ) var id       : String?             = null,
    @SerializedName("name"     ) var name     : String?             = null,
    @SerializedName("sections" ) var sections : ArrayList<SectionsData> = arrayListOf()
) {
}