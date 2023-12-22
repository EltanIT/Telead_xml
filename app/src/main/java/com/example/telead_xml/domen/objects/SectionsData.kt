package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class SectionsData(
    @SerializedName("id"       ) var id       : String?             = null,
    @SerializedName("name"     ) var name     : String?             = null,
    @SerializedName("videoUrl"     ) var videoUrl     : String?             = null,
    @SerializedName("durationInMinutes"     ) var durationInMinutes     : Int?             = null,
) {
}