package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class CallData(
    @SerializedName("name"                 ) val name                 : String?             = null,
    @SerializedName("date"                 ) val date                 : String?             = null,
    @SerializedName("type"                 ) val type                 : String?             = null,
    @SerializedName("image"                 ) val image                 : String?             = "https://mirzhvetov.ru/wp-content/uploads/0/3/7/037a2e23262dd4d733b78734a5a1ae8e.jpeg",
) {
}