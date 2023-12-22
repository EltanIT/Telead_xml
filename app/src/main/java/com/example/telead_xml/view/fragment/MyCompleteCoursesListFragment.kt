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
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentCoursesListBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.MyCompleteCoursesAdapter
import com.example.telead_xml.view.listener.MyCourseListener

class MyCompleteCoursesListFragment : Fragment() {

    private lateinit var binding: FragmentCoursesListBinding
    private lateinit var vm: MyCompleteCoursesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesListBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyCompleteCoursesListViewModelFactory(requireContext()))[MyCompleteCoursesListViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }


    private fun setting() {

    }

    private fun subscription() {
        vm.coursesList.observe(viewLifecycleOwner){
            if (it != null){
                binding.coursesRv.adapter = MyCompleteCoursesAdapter(it, object: MyCourseListener {
                    override fun click(id: String?) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val fragment = MyCoursesLessonsFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("myCoursesLessons")
                            .commit()
                    }
                    override fun clickCertificate(id: String) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val fragment = MyCoursesSertificateFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("myCoursesCertificate")
                            .commit()
                    }

                })
            }
        }
    }
}


class MyCompleteCoursesListViewModel(val context: Context): ViewModel(){
    val coursesList = MutableLiveData(ArrayList<CoursesData>())

    private fun getCourses(){
        coursesList.value?.add(CoursesData())

        coursesList.value = coursesList.value
    }

    fun setting() {
        getCourses()
    }
}

class MyCompleteCoursesListViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyCompleteCoursesListViewModel(context = context) as T
    }
}