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
import com.example.telead_xml.databinding.FragmentCurriculcumBinding
import com.example.telead_xml.domen.objects.LessonData
import com.example.telead_xml.view.adapter.LessonsAdapter
import com.example.telead_xml.view.listener.SectionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CurriculcumFragment : Fragment() {

    private lateinit var binding: FragmentCurriculcumBinding
    private lateinit var vm: CurriculcumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

    private fun subscription() {
        vm.lessonsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.lessonsRv.adapter = LessonsAdapter(it, object: SectionListener {
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


class CurriculcumViewModel(val context: Context): ViewModel(){
    val lessonsList = MutableLiveData<ArrayList<LessonData>>()
    private var id = ""

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private fun getCourses(){
        lessonsList.value?.add(LessonData())

        lessonsList.value = lessonsList.value
    }

    fun setting(bundle: Bundle?) {
        if (bundle!=null){
            id = bundle.getString("id", null)
            getCourses()
        }

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