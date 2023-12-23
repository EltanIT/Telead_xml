package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

data class FilterData(
    @SerializedName("costTypes"       ) val costTypes       : ArrayList<String> = arrayListOf(),
    @SerializedName("formatTypes"     ) val formatTypes     : ArrayList<String> = arrayListOf(),
    @SerializedName("difficultLevels" ) val difficultLevels : ArrayList<String> = arrayListOf(),
    @SerializedName("categoryIds"     ) val categoryIds     : ArrayList<String> = arrayListOf(),
    @SerializedName("minimumRating"   ) var minimumRating   : Double?              = null,
    @SerializedName("count"           ) val count           : Int?              = 2147483647,
    @SerializedName("countSkipped"    ) val countSkipped    : Int?              = 2147483647
)