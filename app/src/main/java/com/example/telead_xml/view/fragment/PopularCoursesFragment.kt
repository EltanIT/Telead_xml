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
import com.example.telead_xml.databinding.FragmentPopularCoursesBinding
import com.example.telead_xml.domen.objects.CategoryData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.PopularCoursesCategoriesHomeAdapter
import com.example.telead_xml.view.adapter.PopularFullCoursesAdapter
import com.example.telead_xml.view.listener.CourseListener

class PopularCoursesFragment : Fragment() {

    private lateinit var binding: FragmentPopularCoursesBinding
    private lateinit var vm: PopularCoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularCoursesBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, PopularCoursesViewModelFactory(requireContext()))[PopularCoursesViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    override fun onResume() {
        vm.setting()
        super.onResume()
    }

    private fun setting() {
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun subscription() {
        vm.popularCoursesList.observe(viewLifecycleOwner){
            if (it != null){
                binding.coursesRv.adapter = PopularFullCoursesAdapter(it, object: CourseListener {
                    override fun click(id: String) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val fragment = SingleCourseDetailsFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("singleCourseDetails")
                            .commit()
                    }

                })
            }
        }
        vm.popularCoursesCategoryList.observe(viewLifecycleOwner){
            if (it != null){
                binding.categoryRv.adapter = PopularCoursesCategoriesHomeAdapter(it)
            }
        }
    }

}


class PopularCoursesViewModel(val context: Context): ViewModel(){
    val popularCoursesList = MutableLiveData(ArrayList<CoursesData>())
    val popularCoursesCategoryList = MutableLiveData(ArrayList<CategoryData>())

    private fun getCourses(){


        popularCoursesList.value = popularCoursesList.value
    }
    private fun getPopularCoursesCategory(){
        popularCoursesCategoryList.value = popularCoursesCategoryList.value
    }

    fun setting() {
        getPopularCoursesCategory()
        getCourses()
    }
}

class PopularCoursesViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PopularCoursesViewModel(context = context) as T
    }
}