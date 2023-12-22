package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class FilterCategoryData(
    @SerializedName("id"                 ) val id                 : String?             = null,
    @SerializedName("name"               ) val name               : String?             = null,
    @SerializedName("check"               ) val check               : Boolean?             = false,
) {
}