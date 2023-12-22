package com.example.telead_xml.view.fragment

import android.annotation.SuppressLint
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
import com.example.telead_xml.databinding.FragmentHomeBinding
import com.example.telead_xml.domen.objects.CategoryData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.MentorData
import com.example.telead_xml.view.adapter.CategoriesHomeAdapter
import com.example.telead_xml.view.adapter.PopularCoursesCategoriesHomeAdapter
import com.example.telead_xml.view.adapter.PopularCoursesHomeAdapter
import com.example.telead_xml.view.adapter.TopMentorHomeAdapter
import com.example.telead_xml.view.listener.CourseListener
import com.example.telead_xml.view.listener.MentorListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var vm: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, HomeViewModelFactory(requireContext()))[HomeViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    override fun onResume() {
        vm.setting()
        super.onResume()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setting() {
        binding.categoryBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, CategoryFragment())
                .addToBackStack("category")
                .commit()
        }
        binding.popularCoursesBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.home_container_view, PopularCoursesFragment())
                .addToBackStack("courses")
                .commit()
        }
        binding.topMentorsBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, TopMentorsFragment())
                .addToBackStack("mentors")
                .commit()
        }

        binding.search.setOnTouchListener { _, _ ->
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, FilterFragment())
                .addToBackStack("search")
                .commit()
            true
        }

        binding.notification.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, NotificationFragment())
                .addToBackStack("notification")
                .commit()
        }
    }

    private fun subscription() {
        vm.categoryList.observe(viewLifecycleOwner){
            if (it != null){
                binding.categoryRv.adapter = CategoriesHomeAdapter(it)
            }
        }
        vm.popularCoursesCategoryList.observe(viewLifecycleOwner){
            if (it != null){
                binding.coursesCategoryRv.adapter = PopularCoursesCategoriesHomeAdapter(it)
            }
        }
        vm.popularCoursesList.observe(viewLifecycleOwner){
            if (it != null){
                binding.coursesRv.adapter = PopularCoursesHomeAdapter(it, object: CourseListener{
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
        vm.topMentorList.observe(viewLifecycleOwner){
            if (it != null){
                binding.mentorRv.adapter = TopMentorHomeAdapter(it, object: MentorListener{
                    override fun click(position: Int) {
                        val bundle = Bundle()
                        bundle.putInt("id", position)
                        val fragment = SingleMentorsDetailsFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("singleMentorDetails")
                            .commit()
                    }

                })
            }
        }
    }

}


class HomeViewModel(val context: Context): ViewModel(){
    val categoryList = MutableLiveData(ArrayList<CategoryData>())
    val popularCoursesCategoryList = MutableLiveData(ArrayList<CategoryData>())
    val popularCoursesList = MutableLiveData(ArrayList<CoursesData>())
    val topMentorList = MutableLiveData(ArrayList<MentorData>())

    private fun getCategory(){
        categoryList.value?.add(CategoryData("3D Design"))
        categoryList.value?.add(CategoryData("Arts & Humanities"))
        categoryList.value?.add(CategoryData("Graphic Design"))
        categoryList.value = categoryList.value
    }

    private fun getPopularCoursesCategory(){
        popularCoursesCategoryList.value?.add(CategoryData("All"))
        popularCoursesCategoryList.value?.add(CategoryData("3D Design"))
        popularCoursesCategoryList.value?.add(CategoryData("Arts & Humanities"))
        popularCoursesCategoryList.value?.add(CategoryData("Graphic Design"))
        popularCoursesCategoryList.value = popularCoursesCategoryList.value
    }

    private fun getPopularCourses(){

        popularCoursesList.value = popularCoursesList.value
    }

    private fun getTopMentor(){
        topMentorList.value?.add(MentorData("Вадим", "William", "S", "3D Design", ""))
        topMentorList.value?.add(MentorData("Игорь", "William", "S", "3D Design", ""))
        topMentorList.value?.add(MentorData("Николай", "William", "S", "3D Design", ""))
        topMentorList.value?.add(MentorData("Виктория", "William", "S", "3D Design", ""))
        topMentorList.value = topMentorList.value
    }

    fun setting() {
        getCategory()
        getPopularCoursesCategory()
        getPopularCourses()
        getTopMentor()
    }

}

class HomeViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(context = context) as T
    }
}