package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

data class InstructorData(
    @SerializedName("name") var name: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("image") val image: String? = null
) {
}