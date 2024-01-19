package com.example.telead_xml.view.fragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCourseByIdRepository
import com.example.telead_xml.databinding.FragmentCurriculcumBinding
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

class CurriculcumFragment : Fragment() {

    private lateinit var binding: FragmentCurriculcumBinding
    private lateinit var vm: CurriculcumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentCurriculcumBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, CurriculcumViewModelFactory(requireContext()))[CurriculcumViewModel::class.java]
        subscription()
        setting()
        vm.setting(arguments)
        return binding.root
    }

    private fun setting() {
        binding.enroll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", vm.getId())
            val fragment = PayMentMethodsFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, fragment)
                .addToBackStack("paymentMethods")
                .commit()
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun subscription() {
        vm.sectionsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.lessonsRv.adapter = SectionAdapter(it, object: SectionListener {
                    override fun play(videoUrl: String) {
                        val bundle = Bundle()
                        bundle.putString("videoUrl", videoUrl)
                        val fragment = MyCourseOngoingVideoFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("myCourseOngoingVideo")
                            .commit()
                    }
                }, false)
            }
        }
    }
}


class CurriculcumViewModel(val context: Context): ViewModel(){
    val sectionsList = MutableLiveData<ArrayList<SectionData>>()
    private var id: String? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private fun getCourses(){
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
                }
            }else{
                val list = ArrayList<LessonData>()
                val list2 = ArrayList<LessonData>()
                list.add(LessonData(name = "Где используют графический дизайн", durationInMinutes = 15))
                list.add(LessonData(name = "Установка необходимого ПО", durationInMinutes = 20))

                list2.add(LessonData(name = "Разбор палитры инструментов", durationInMinutes = 35))
                list2.add(LessonData(name = "Создание логотипа из текста", durationInMinutes = 26))
                list2.add(LessonData(name = "Немного из фотомонтажа", durationInMinutes = 15))
                list2.add(LessonData(name = "Первый дизайн", durationInMinutes = 40))

                sectionsList.value?.add(SectionData(name = "Введение", sections = list))
                sectionsList.value?.add(SectionData(name = "Основная часть", sections = list2))

                sectionsList.postValue(sectionsList.value)
            }
        }
    }

    fun setting(bundle: Bundle?) {
        if (bundle!=null){
            id = bundle.getString("id", "")
        }
        getCourses()
    }

    fun getId(): String? {
        return id
    }
}

class CurriculcumViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurriculcumViewModel(context = context) as T
    }
}