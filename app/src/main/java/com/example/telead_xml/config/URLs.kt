package com.example.telead_xml.config

import java.net.URL

class URLs {
    private val mainUrl = "https://7931-176-28-64-201.ngrok-free.app/api/"

    //Auth
    val signupUrl = URL(mainUrl+"signup")
    val signinUrl = URL(mainUrl+"signin")
    val tokenUrl = URL(mainUrl+"token")
    val codeRecoveryUrl = URL(mainUrl+"code/recovery")
    val codeVerifyUrl = URL(mainUrl+"code/verify")
    val resetPasswordUrl = URL(mainUrl+"reset-password")

    //Profile
    val putProfileUrl = URL(mainUrl+"profile")
    val getProfileUrl = URL(mainUrl+"profile")

    //Bookmark
    val postBookmark = URL(mainUrl+"bookmark")
    val getBookmark = URL(mainUrl+"bookmark")
    val removeBookmark = mainUrl+"bookmark/"

    //Course
    val postCourseUrl = URL(mainUrl+"course")
    val getCourseForFilterUrl = URL(mainUrl+"courses")
    val getCourseForId = mainUrl+"course/"
    val getCourseBenefitsUrl = mainUrl+"course-benefits"

    //CourseCategory
    val getCourseCategoriesUrl = URL(mainUrl+"course-categories")
    val createCourseCategoriesUrl = URL(mainUrl+"course-category")
}