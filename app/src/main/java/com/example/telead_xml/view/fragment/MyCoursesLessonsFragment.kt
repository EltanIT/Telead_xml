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
import com.example.telead_xml.databinding.FragmentMyCoursesLessonsBinding
import com.example.telead_xml.domen.objects.SectionData
import com.example.telead_xml.view.adapter.MyLessonsAdapter
import com.example.telead_xml.view.listener.SectionListener

class MyCoursesLessonsFragment : Fragment() {

    private lateinit var binding: FragmentMyCoursesLessonsBinding
    private lateinit var vm: MyCoursesLessonsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCoursesLessonsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyCoursesLessonsViewModelFactory(requireContext()))[MyCoursesLessonsViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.lessonsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.lessonsRv.adapter = MyLessonsAdapter(it, object: SectionListener{
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
                })
            }
        }
    }
}


class MyCoursesLessonsViewModel(val context: Context): ViewModel(){
    val lessonsList = MutableLiveData<ArrayList<SectionData>>()

    private fun getCourses(){
        lessonsList.value?.add(SectionData())

        lessonsList.value = lessonsList.value
    }

    fun setting() {
        getCourses()
    }
}

class MyCoursesLessonsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyCoursesLessonsViewModel(context = context) as T
    }
}