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
import com.example.telead_xml.databinding.FragmentMyCoursesOngoingLessonsBinding
import com.example.telead_xml.domen.objects.LessonData
import com.example.telead_xml.view.adapter.MyLessonsAdapter
import com.example.telead_xml.view.listener.SectionListener

class MyCoursesOngoingLessonsFragment : Fragment() {

    private lateinit var binding: FragmentMyCoursesOngoingLessonsBinding
    private lateinit var vm: MyCoursesOngoingLessonsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCoursesOngoingLessonsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyCoursesOngoingLessonsViewModelFactory(requireContext()))[MyCoursesOngoingLessonsViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }

    private fun subscription() {
        vm.lessonsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.lessonsRv.adapter = MyLessonsAdapter(it, object:SectionListener{
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

    private fun setting() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}


class MyCoursesOngoingLessonsViewModel(val context: Context): ViewModel(){
    val lessonsList = MutableLiveData(ArrayList<LessonData>())

    private fun getCourses(){

        lessonsList.value = lessonsList.value
    }

    fun setting() {
        getCourses()
    }
}

class MyCoursesOngoingLessonsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyCoursesOngoingLessonsViewModel(context = context) as T
    }
}