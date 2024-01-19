package com.example.telead_xml.domen.objects

import com.google.gson.annotations.SerializedName

class ProfileData(
    @SerializedName("email"    ) var email    : String? = null,
    @SerializedName("fullName" ) var fullName : String? = null,
    @SerializedName("nickname" ) var nickname : String? = null,
    @SerializedName("dob"      ) var dob      : String? = null,
    @SerializedName("phone"    ) var phone    : String? = null,
    @SerializedName("role"     ) var role     : String? = null,
    @SerializedName("gender"   ) var gender   : String? = null,
    @SerializedName("urlIcon"  ) var urlIcon  : String? = null)