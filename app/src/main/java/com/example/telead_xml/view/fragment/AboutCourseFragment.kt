package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.databinding.FragmentAboutCourseBinding
import com.example.telead_xml.domen.objects.InstructorData
import com.example.telead_xml.domen.objects.ReviewData
import com.example.telead_xml.domen.objects.SkillData
import com.example.telead_xml.view.adapter.ReviewsAdapter
import com.example.telead_xml.view.adapter.SkillsAdapter

class AboutCourseFragment(val description: String?) : Fragment() {

    private lateinit var binding: FragmentAboutCourseBinding
    private lateinit var vm: AboutCourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutCourseBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, AboutCourseViewModelFactory(requireContext(), description))[AboutCourseViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.skills.observe(viewLifecycleOwner){
            if (it!=null){
                binding.skillsRv.adapter = SkillsAdapter(it)
            }
        }
        vm.reviews.observe(viewLifecycleOwner){
            if (it!=null){
                binding.skillsRv.adapter = ReviewsAdapter(it)
            }
        }

        vm.descriptionData.observe(viewLifecycleOwner){
            if (it!=null){
                binding.description.text = it
            }
        }
    }

}


class AboutCourseViewModel(val context: Context, val description: String?): ViewModel(){
    val skills = MutableLiveData(ArrayList<SkillData>())
    val descriptionData = MutableLiveData(description)

    val instructor = MutableLiveData(
        InstructorData(
            "Design Principles: Organizing ..",
            "Graphic Design",
        "")
    )

    val reviews = MutableLiveData(ArrayList<ReviewData>())


    fun setting(){
       getSkills()
    }

    private fun getSkills() {
        skills.value?.add(SkillData("25 уроков", ""))
        skills.value?.add(SkillData("25 уроков", ""))
        skills.value?.add(SkillData("25 уроков", ""))
        skills.value?.add(SkillData("25 уроков", ""))
        skills.value?.add(SkillData("25 уроков", ""))

        skills.value = skills.value
    }
    private fun getReviews() {
        reviews.value?.add(ReviewData("William S. Cunningham",
            "The Course is Very Good dolor sit amet, consect tur adipiscing elit. Naturales divitias dixit parab les esse, quod parvo",
        4.3,
        564,
        "2 Недели Назад"))
        reviews.value?.add(ReviewData("William S. Cunningham",
            "The Course is Very Good dolor sit amet, consect tur adipiscing elit. Naturales divitias dixit parab les esse, quod parvo",
            4.3,
            564,
            "2 Недели Назад"))


        reviews.value = reviews.value
    }
}

class AboutCourseViewModelFactory(val context: Context, val description: String?): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AboutCourseViewModel(context = context, description) as T
    }
}