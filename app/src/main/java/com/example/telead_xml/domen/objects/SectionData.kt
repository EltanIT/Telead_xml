package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class SectionData(
    @SerializedName("id"       ) var id       : String?             = null,
    @SerializedName("name"     ) var name     : String?             = null,
    @SerializedName("duration"     ) var duration     : Int?             = null,
    @SerializedName("sections" ) var sections : ArrayList<LessonData> = ArrayList()
) {
}