package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

data class ReviewData(
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("rating") val rating: Float? = null,
    @SerializedName("likes") val likes: Int? = null,
    @SerializedName("date") val date: String? = null,
) {
}