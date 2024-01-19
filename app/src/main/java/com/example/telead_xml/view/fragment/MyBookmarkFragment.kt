package com.example.telead_xml.view.fragment

import GetBookmarkRepository
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCoursesForFiltersRepository
import com.example.telead_xml.data.repository.course_category.GetCoursesCategoriesRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.databinding.FragmentMyBookmarkBinding
import com.example.telead_xml.domen.objects.CategoryData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.MyBookmarkAdapter
import com.example.telead_xml.view.adapter.PopularCoursesCategoriesHomeAdapter
import com.example.telead_xml.view.listener.BookmarkListener
import com.example.telead_xml.view.listener.CategoryListener
import com.example.telead_xml.view.listener.RemoveBookmarkListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MyBookmarkFragment : Fragment() {

    private lateinit var binding: FragmentMyBookmarkBinding
    private lateinit var vm: MyBookmarkViewModel

    private lateinit var adapter: MyBookmarkAdapter

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
        vm.bookmarkList.observe(viewLifecycleOwner){
            if (it!=null){
                adapter = MyBookmarkAdapter(it, object: BookmarkListener{
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
                    override fun remove(id: String) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val fragment = RemoveMyBookmarkFragment(object: RemoveBookmarkListener{
                            override fun remove(id: String) {
                                adapter.remove(id)
                                vm.remove(id)
                            }

                        })
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("removeBookmark")
                            .commit()
                    }
                })
                binding.coursesRv.adapter = adapter
            }
        }
        vm.categoryList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.categoryRv.adapter = PopularCoursesCategoriesHomeAdapter(it, object: CategoryListener{
                    override fun click(name: String?) {
                        adapter.filter(name)
                    }

                })
            }
        }
    }
}


class MyBookmarkViewModel(val context: Context): ViewModel(){
    val bookmarkList = MutableLiveData(ArrayList<CoursesData>())
    val categoryList = MutableLiveData(ArrayList<CategoryData>())

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

   fun setting(){
       getCategory()
       getList()
   }

    private fun getList() {
        coroutineScope.launch {
            val responseData = GetBookmarkRepository().request(GetAccessToken().execute(context)?:"")
            if(responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ArrayList<CoursesData>>(){}.type
                Log.i("swagger", responseData.body.toString())
                try{
                    bookmarkList.value?.addAll(gson.fromJson(responseData.body?:"",type))
                    bookmarkList.postValue(bookmarkList.value)
                }catch (e: Exception){
                    bookmarkList.value?.addAll(ArrayList())
                }
            }else{
                bookmarkList.value?.add(CoursesData())
                bookmarkList.value?.add(CoursesData())
                bookmarkList.value?.add(CoursesData())
                bookmarkList.postValue(bookmarkList.value)
            }
        }
    }

    private fun getCategory(){
        coroutineScope.launch {
            val responseData = GetCoursesCategoriesRepository().request(GetAccessToken().execute(context)?:"")
            if(responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ArrayList<CategoryData>>(){}.type
                Log.i("swagger", responseData.body.toString())
                try{
                    categoryList.value?.add(CategoryData(name = "Все"))
                    categoryList.value?.addAll(gson.fromJson(responseData.body?:"",type))
                    categoryList.postValue(categoryList.value)
                }catch (e: Exception){
                    categoryList.value?.addAll(ArrayList())
                }
            }else{
                categoryList.value?.add(CategoryData(name = "Все"))
                categoryList.value?.add(CategoryData(name = "3Д Дизайн"))
                categoryList.value?.add(CategoryData(name = "Искусство и гуманитарные науки"))
                categoryList.value?.add(CategoryData(name = "Графический Дизайн"))
                categoryList.postValue(categoryList.value)
            }
        }
    }

    fun remove(id: String) {
        bookmarkList.value?.removeIf {
            it.id?.equals(id) == true
        }
    }
}

class MyBookmarkViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyBookmarkViewModel(context = context) as T
    }
}