package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class CoursesData(
    @SerializedName("id"                 ) val id                 : String?             = null,
    @SerializedName("name"               ) val name               : String?             = null,
    @SerializedName("description"        ) val description        : String?             = null,
    @SerializedName("rating"             ) val rating             : Int?                = null,
    @SerializedName("costType"           ) val costType           : String?             = null,
    @SerializedName("formatType"         ) val formatType         : String?             = null,
    @SerializedName("difficultLevelType" ) val difficultLevelType : String?             = null,
    @SerializedName("price"              ) val price              : Int?                = null,
    @SerializedName("durationInMinutes"  ) val durationInMinutes  : Int?                = null,
    @SerializedName("countStudents"      ) val countStudents      : Int?                = null,
    @SerializedName("imageUrl"           ) val imageUrl           : String?             = null,
    @SerializedName("benefits"           ) val benefits           : ArrayList<BenefitData> = arrayListOf()
) {
}