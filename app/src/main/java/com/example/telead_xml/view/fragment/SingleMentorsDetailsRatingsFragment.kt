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
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentCoursesListBinding
import com.example.telead_xml.databinding.FragmentSingleMentorsDetailsRatingsBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.ReviewData
import com.example.telead_xml.view.adapter.MentorReviewsAdapter
import com.example.telead_xml.view.adapter.PopularFullCoursesAdapter
import com.example.telead_xml.view.adapter.ReviewsAdapter
import com.example.telead_xml.view.listener.CourseListener

class SingleMentorsDetailsRatingsFragment : Fragment() {

    private lateinit var binding: FragmentSingleMentorsDetailsRatingsBinding
    private lateinit var vm: SingleMentorsDetailsRatingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleMentorsDetailsRatingsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, SingleMentorsDetailsRatingsViewModelFactory(requireContext()))[SingleMentorsDetailsRatingsViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }


    private fun setting() {

    }

    private fun subscription() {
        vm.popularCoursesList.observe(viewLifecycleOwner){
            if (it != null){
                binding.ratingRv.adapter = MentorReviewsAdapter(it)
            }
        }
    }

}


class SingleMentorsDetailsRatingsViewModel(val context: Context): ViewModel(){
    val popularCoursesList = MutableLiveData(ArrayList<ReviewData>())

    private fun getCourses(){
        popularCoursesList.value?.add(ReviewData("Heather S. McMullen", "The Course is Very Good dolor sit amet, con sect tur adipiscing elit. Naturales divitias dixit parab les esse..", 4.3f, 730, "2 Недели Назад"))

        popularCoursesList.value = popularCoursesList.value
    }

    fun setting() {
        getCourses()
    }
}

class SingleMentorsDetailsRatingsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleMentorsDetailsRatingsViewModel(context = context) as T
    }
}