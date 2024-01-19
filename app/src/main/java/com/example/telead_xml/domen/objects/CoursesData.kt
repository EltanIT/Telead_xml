package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

data class CoursesData(
    @SerializedName("id"                 ) val id                 : String?             = null,
    @SerializedName("name"               ) val name               : String?             = "Продвинутый графический дизайн",
    @SerializedName("description"        ) val description        : String?             = null,
    @SerializedName("rating"             ) val rating             : Double?             = 4.3,
    @SerializedName("costType"           ) val costType           : String?             = null,
    @SerializedName("formatType"         ) val formatType         : String?             = null,
    @SerializedName("difficultLevelType" ) val difficultLevelType : String?             = null,
    @SerializedName("price"              ) val price              : Int?                = 28,
    @SerializedName("durationInMinutes"  ) val durationInMinutes  : Int?                = null,
    @SerializedName("countStudents"      ) val countStudents      : Int?                = 7563,
    @SerializedName("imageUrl"           ) val imageUrl           : String?             = "https://gk-c.ru/wp-content/uploads/2022/01/graficheskiy-dizayner.jpg",
    @SerializedName("benefits"           ) val benefits           : ArrayList<BenefitData> = ArrayList()
) {
}