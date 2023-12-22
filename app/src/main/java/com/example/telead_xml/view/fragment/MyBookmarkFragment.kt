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
import com.example.telead_xml.databinding.FragmentMyBookmarkBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.MyBookmarkAdapter
import com.example.telead_xml.view.listener.BookmarkListener

class MyBookmarkFragment : Fragment() {

    private lateinit var binding: FragmentMyBookmarkBinding
    private lateinit var vm: MyBookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBookmarkBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyBookmarkViewModelFactory(requireContext()))[MyBookmarkViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.coursesList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.coursesRv.adapter = MyBookmarkAdapter(it, object: BookmarkListener{
                    override fun click(position: Int) {
                        val bundle = Bundle()
                        bundle.putInt("id", position)
                        val fragment = RemoveMyBookmarkFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("removeBookmark")
                            .commit()
                    }

                })
            }
        }
    }

}


class MyBookmarkViewModel(val context: Context): ViewModel(){
    val coursesList = MutableLiveData(ArrayList<CoursesData>())


   fun setting(){
       getList()
   }

    private fun getList() {
        coursesList.value = coursesList.value
    }
}

class MyBookmarkViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyBookmarkViewModel(context = context) as T
    }
}