package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class BenefitData(
    @SerializedName("name"               ) val name               : String?             = null,
    @SerializedName("countItems"        ) val countItems        : Int?             = null,
    @SerializedName("imageUrl"             ) val imageUrl             : Int?                = null,
) {
}