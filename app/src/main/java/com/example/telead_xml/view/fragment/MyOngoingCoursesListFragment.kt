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
import com.example.telead_xml.databinding.FragmentCoursesListBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.OngoingCoursesData
import com.example.telead_xml.view.adapter.MyCompleteCoursesAdapter
import com.example.telead_xml.view.adapter.MyOngoingCoursesAdapter

class MyOngoingCoursesListFragment : Fragment() {

    private lateinit var binding: FragmentCoursesListBinding
    private lateinit var vm: MyOngoingCoursesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesListBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyOngoingCoursesListViewModelFactory(requireContext()))[MyOngoingCoursesListViewModel::class.java]
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
                binding.coursesRv.adapter = MyOngoingCoursesAdapter(it)
            }
        }
    }

}


class MyOngoingCoursesListViewModel(val context: Context): ViewModel(){
    val popularCoursesList = MutableLiveData(ArrayList<OngoingCoursesData>())

    private fun getCourses(){
        popularCoursesList.value?.add(OngoingCoursesData("Graphic Design Advanced", "Graphic Design",4.2, "2 Ч 36 Мин", 93, 125, ""))
        popularCoursesList.value?.add(OngoingCoursesData("Graphic Design Advanced", "Graphic Design",4.2, "2 Ч 36 Мин", 93, 125, ""))
        popularCoursesList.value?.add(OngoingCoursesData("Graphic Design Advanced", "Graphic Design",4.2, "2 Ч 36 Мин", 93, 125, ""))
        popularCoursesList.value?.add(OngoingCoursesData("Graphic Design Advanced", "Graphic Design",4.2, "2 Ч 36 Мин", 93, 125, ""))
        popularCoursesList.value?.add(OngoingCoursesData("Graphic Design Advanced", "Graphic Design",4.2, "2 Ч 36 Мин", 93, 125, ""))
        popularCoursesList.value?.add(OngoingCoursesData("Graphic Design Advanced", "Graphic Design",4.2, "2 Ч 36 Мин", 93, 125, ""))

        popularCoursesList.value = popularCoursesList.value
    }

    fun setting() {
        getCourses()
    }
}

class MyOngoingCoursesListViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyOngoingCoursesListViewModel(context = context) as T
    }
}