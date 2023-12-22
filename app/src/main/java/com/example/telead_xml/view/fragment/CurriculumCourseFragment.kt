package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.databinding.FragmentCurriculumCourseBinding
import com.example.telead_xml.domen.objects.SingleCourseData
import com.google.gson.Gson

class CurriculumCourseFragment : Fragment() {

    private lateinit var binding: FragmentCurriculumCourseBinding
    private lateinit var vm: CurriculumCourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurriculumCourseBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, CurriculumCourseViewModelFactory(requireContext()))[CurriculumCourseViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {

    }

}


class CurriculumCourseViewModel(val context: Context): ViewModel(){
    val courseDetails = MutableLiveData(
        SingleCourseData("Design Principles: Organizing ..",
            "Graphic Design",
            21,
            "15 часов",
            13)
    )


    fun getBundle(bundle: Bundle?){
        if (bundle!=null){
            val courseJson = bundle.getString("course", null)
            if (courseJson!=null){
                val gson = Gson()
                courseDetails.value = gson.fromJson(courseJson, SingleCourseData::class.java)
            }
        }
    }
}

class CurriculumCourseViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurriculumCourseViewModel(context = context) as T
    }
}