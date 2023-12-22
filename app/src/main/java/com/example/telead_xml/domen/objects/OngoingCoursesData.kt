package com.example.telead_xml.domen.objects

class OngoingCoursesData(
    val name: String,
    val category: String,
    val rating: Double,
    val time: String,
    val completed: Int,
    val total: Int,
    val image: String = ""
) {
}