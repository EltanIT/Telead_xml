package com.example.telead_xml.view.fragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentAboutCourseBinding
import com.example.telead_xml.domen.objects.InstructorData
import com.example.telead_xml.domen.objects.ReviewData
import com.example.telead_xml.domen.objects.SkillData
import com.example.telead_xml.view.adapter.ReviewsAdapter
import com.example.telead_xml.view.adapter.SkillsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AboutCourseFragment() : Fragment() {

    private lateinit var binding: FragmentAboutCourseBinding
    private lateinit var vm: AboutCourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding = FragmentAboutCourseBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, AboutCourseViewModelFactory(requireContext()))[AboutCourseViewModel::class.java]
        subscription()
        setting()
        vm.setting(arguments)
        return binding.root
    }

    private fun setting() {
        binding.moreReviews.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", vm.getId())
            val fragment = ReviewsFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, fragment)
                .addToBackStack("reviews")
                .commit()
        }
    }

    private fun subscription() {
        vm.skills.observe(viewLifecycleOwner){
            if (it!=null){
                binding.skillsRv.adapter = SkillsAdapter(it)
            }
        }
        vm.reviews.observe(viewLifecycleOwner){
            if (it!=null){
                binding.reviewsRv.adapter = ReviewsAdapter(it)
            }
        }

        vm.description.observe(viewLifecycleOwner){
            if (it!=null){
                binding.description.text = it
            }
        }

        vm.instructor.observe(viewLifecycleOwner){
            if (it!=null){
                binding.nameInstructor.text = it.name
                binding.categoryInstructor.text = it.category
            }else{

            }
        }
    }

}


class AboutCourseViewModel(val context: Context): ViewModel(){
    val skills = MutableLiveData(ArrayList<SkillData>())
    val description = MutableLiveData<String?>()
    val instructor = MutableLiveData(InstructorData(
        name = "Николай Брусилович",
        category = "Дизайнер"))

    val reviews = MutableLiveData(ArrayList<ReviewData>())
    private var id = ""

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    fun setting(bundle: Bundle?){
        if (bundle!=null){
            id = bundle.getString("id", "")
            description.value = bundle.getString("description", "")
        }
        description.value = "Графический дизайн в настоящее время популярная профессия графический дизайн вне вашей карьеры о tantas regiones barbarorum pedibus obiit"
        getSkills(id?:"")
        getInstructor(id?:"")
        getReviews()
    }

    fun getInstructor(id: String){
        coroutineScope.launch {

        }
    }

    private fun getSkills(id: String) {
        skills.value = ArrayList()

        skills.value?.add(SkillData("25 уроков", ""))
        skills.value?.add(SkillData("Готовые дизайны и модели", ""))
        skills.value?.add(SkillData("Аудио книгу", ""))
        skills.value?.add(SkillData("Разработанный тобой лично дизайн сайта", ""))
        skills.value?.add(SkillData("Сертификат об окончании", ""))

        skills.value = skills.value
    }
    private fun getReviews() {
        reviews.value?.add(ReviewData(name = "Валерий Пакуситский", rating = 4.8f, date =  "2 Недели Назад", likes = 761,
            description = "Курс очень хорош, долор ветерм, как всегда, как всегда, привет капитану.."))
        reviews.value?.add(ReviewData(name = "Наташа Гончарова", rating = 4.6f, date =  "2 Недели Назад", likes = 532,
            description = "Курс очень хорош, долор ветерм, как всегда, как всегда, привет капитану.."))

        reviews.value = reviews.value
    }

    fun getId(): String? {
        return id
    }
}

class AboutCourseViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AboutCourseViewModel(context = context) as T
    }
}