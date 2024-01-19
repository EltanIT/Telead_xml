package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

data class FilterData(
    @SerializedName("costTypes"       ) val costTypes       : ArrayList<String> = arrayListOf(),
    @SerializedName("formatTypes"     ) val formatTypes     : ArrayList<String> = arrayListOf(),
    @SerializedName("difficultLevels" ) val difficultLevels : ArrayList<String> = arrayListOf(),
    @SerializedName("categoryIds"     ) val categoryIds     : ArrayList<String> = arrayListOf(),
    @SerializedName("minimumRating"   ) var minimumRating   : Double?              = 0.0,
    @SerializedName("count"           ) val count           : Int?              = 10,
    @SerializedName("countSkipped"    ) var countSkipped    : Int?              = 0
)