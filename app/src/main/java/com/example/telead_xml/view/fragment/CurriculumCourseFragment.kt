package com.example.telead_xml.view.fragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCourseByIdRepository
import com.example.telead_xml.databinding.FragmentCurriculumCourseBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.LessonData
import com.example.telead_xml.domen.objects.SectionData
import com.example.telead_xml.view.adapter.SectionAdapter
import com.example.telead_xml.view.listener.SectionListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CurriculumCourseFragment() : Fragment() {

    private lateinit var binding: FragmentCurriculumCourseBinding
    private lateinit var vm: CurriculumCourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding = FragmentCurriculumCourseBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, CurriculumCourseViewModelFactory(requireContext()))[CurriculumCourseViewModel::class.java]
        subscription()
        vm.setting(arguments)
        return binding.root
    }


    private fun subscription() {
        vm.sectionsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.lessonsRv.adapter = SectionAdapter(it, object: SectionListener{
                    override fun play(id: String) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val frag = MyCourseOngoingVideoFragment()
                        frag.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.full_home_container_view, frag)
                            .addToBackStack("video")
                            .commit()
                    }
                }, true)
            }
        }
    }

}


class CurriculumCourseViewModel(val context: Context): ViewModel(){
    val sectionsList = MutableLiveData(ArrayList<SectionData>())

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun setting(bundle: Bundle?) {
        val id = ""
        if (bundle!=null){
            val id = bundle.getString("id", "")
        }
        getSections(id)
    }

    private fun getSections(id: String?) {
        sectionsList.value = ArrayList()
        coroutineScope.launch {
            val responseData = GetCourseByIdRepository().request( id?:"")
            if(responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ArrayList<CoursesData>>(){}.type
                Log.i("swagger", responseData.body.toString())
                try{
                    sectionsList.postValue(gson.fromJson(responseData.body?:"",type))
                }catch (e: Exception){
                    sectionsList.value?.addAll(ArrayList())
                }
            }else{
                val list = ArrayList<LessonData>()
                val list2 = ArrayList<LessonData>()
                list.add(LessonData(name = "Где используют графический дизайн", durationInMinutes = 15))
                list.add(LessonData(name = "Установка необходимого ПО", durationInMinutes = 20))

                list2.add(LessonData(name = "Разбор палитры инструментов", durationInMinutes = 35))
                list2.add(LessonData(name = "Создание логотипа из текста", durationInMinutes = 26))

                sectionsList.value?.add(SectionData(name = "Введение", sections = list))
//                sectionsList.value?.add(SectionData(name = "Основная часть", sections = list2))

                sectionsList.postValue(sectionsList.value)
            }
        }
    }

    fun play(id: String) {

    }
}

class CurriculumCourseViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurriculumCourseViewModel(context = context) as T
    }
}