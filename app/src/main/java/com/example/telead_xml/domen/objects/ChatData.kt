package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class ChatData(
    @SerializedName("name"                 ) val name                 : String?             = null,
    @SerializedName("time"                 ) val time                 : String?             = null,
    @SerializedName("message"                 ) val message                 : Int?             = null,
    @SerializedName("lastMessage"                 ) val lastMessage                 : String?             = null,
    @SerializedName("image"                 ) val image                 : String?             = "https://mirzhvetov.ru/wp-content/uploads/0/3/7/037a2e23262dd4d733b78734a5a1ae8e.jpeg",
) {
}