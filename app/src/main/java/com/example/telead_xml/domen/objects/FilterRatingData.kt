package com.example.telead_xml.domen.objects

import okhttp3.Response

class FilterRatingData(
    val name: String,
    val rating: Double,
    val check: Boolean = false
) {
}