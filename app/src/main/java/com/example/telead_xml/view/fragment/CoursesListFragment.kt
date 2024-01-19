package com.example.telead_xml.view.fragment

import PostBookmarkRepository
import RemoveBookmarkRepository
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
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.databinding.FragmentCoursesListBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.MentorCoursesAdapter
import com.example.telead_xml.view.listener.CourseListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoursesListFragment(val request: String) : Fragment() {

    private lateinit var binding: FragmentCoursesListBinding
    private lateinit var vm: CoursesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesListBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, CoursesListViewModelFactory(requireContext()))[CoursesListViewModel::class.java]
        subscription()
        setting()
        vm.setting(request)
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.coursesList.observe(viewLifecycleOwner){
            if (it != null){
                binding.coursesRv.adapter = MentorCoursesAdapter(it, object: CourseListener {
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
                        vm.removeBookmark(id)
                    }

                })
            }
        }
    }

}


class CoursesListViewModel(val context: Context): ViewModel(){
    val coursesList = MutableLiveData(ArrayList<CoursesData>())
    val statePost = MutableLiveData<Int?>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val postBookmarkRepository = PostBookmarkRepository()
    private val removeBookmarkRepository = RemoveBookmarkRepository()

    private fun getCourses(request: String) {
        coroutineScope.launch {

        }
        coursesList.value?.add(CoursesData())
        coursesList.value?.add(CoursesData())
        coursesList.value?.add(CoursesData())
        coursesList.value?.add(CoursesData())

        coursesList.value = coursesList.value
    }

    fun setting(request: String) {
        getCourses(request)
    }

    fun addBookmark(id: String?) {
        coroutineScope.launch {
            coroutineScope.launch {
                val responseData = postBookmarkRepository.request(id?:"", GetAccessToken().execute(context)?:"")
                statePost.postValue(responseData?.response?.code)
            }
        }
    }

    fun removeBookmark(id: String?) {
        coroutineScope.launch {
            val responseData = removeBookmarkRepository.request(id?:"", GetAccessToken().execute(context)?:"")
            statePost.postValue(responseData?.response?.code)
        }
    }
}

class CoursesListViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoursesListViewModel(context = context) as T
    }
}