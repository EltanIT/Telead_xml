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
import com.example.telead_xml.view.adapter.PopularFullCoursesAdapter
import com.example.telead_xml.view.listener.CourseListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MentorsCoursesListFragment(val request: String) : Fragment() {

    private lateinit var binding: FragmentCoursesListBinding
    private lateinit var vm: MentorsCoursesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesListBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MentorsCoursesListViewModelFactory(requireContext()))[MentorsCoursesListViewModel::class.java]
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
                binding.coursesRv.adapter = PopularFullCoursesAdapter(it, object: CourseListener {
                    override fun click(id: String?) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val fragment = SingleCourseDetailsFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("singleCourseDetails")
                            .commit()
                    }

                    override fun addBookmark(id: String?) {
                        vm.addBookmark(id)
                    }

                    override fun removeBookmark(id: String?) {

                    }

                })
            }
        }
    }

}


class MentorsCoursesListViewModel(val context: Context): ViewModel(){
    val popularCoursesList = MutableLiveData(ArrayList<CoursesData>())

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private fun getCourses(){

        popularCoursesList.value = popularCoursesList.value
    }

    fun setting() {
        getCourses()
    }

    fun addBookmark(id: String?) {
        coroutineScope.launch{

        }
    }
}

class MentorsCoursesListViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MentorsCoursesListViewModel(context = context) as T
    }
}