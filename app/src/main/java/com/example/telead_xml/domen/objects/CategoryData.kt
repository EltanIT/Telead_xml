package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class CategoryData(
    @SerializedName("id"                 ) val id                 : String?             = null,
    @SerializedName("name"               ) val name               : String?             = null,
    @SerializedName("imageUrl"               ) val imageUrl               : String?             = null,
) {
}