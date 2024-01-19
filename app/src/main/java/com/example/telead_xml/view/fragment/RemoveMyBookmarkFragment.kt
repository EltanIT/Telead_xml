package com.example.telead_xml.view.fragment

import RemoveBookmarkRepository
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCourseByIdRepository
import com.example.telead_xml.data.repository.course.GetCoursesForFiltersRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.databinding.FragmentRemoveMyBookmarkBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.listener.RemoveBookmarkListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RemoveMyBookmarkFragment(val listener: RemoveBookmarkListener) : Fragment() {

    private lateinit var binding: FragmentRemoveMyBookmarkBinding
    private lateinit var vm: RemoveMyBookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRemoveMyBookmarkBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, RemoveMyBookmarkViewModelFactory(requireContext()))[RemoveMyBookmarkViewModel::class.java]
        subscription()
        setting()
        vm.setting(arguments,listener)
        return binding.root
    }

    override fun onResume() {
        openView()
        super.onResume()
    }

    private fun setting() {
        binding.cancal.setOnClickListener {
           closeView()
        }
        binding.remove.setOnClickListener {
            vm.remove()
        }
        binding.backView.setOnClickListener {
            closeView()
        }
        binding.cardView.setOnClickListener {}
    }

    private fun subscription() {
        vm.course.observe(viewLifecycleOwner){
            if (it!=null){
                binding.name.text = it.name
                if (it.benefits.size > 0){
                    binding.category.text = it.benefits[0].name
                }
                binding.price.text = it.price.toString()
                binding.priceFull.text = it.price.toString()
                binding.rating.text = it.rating.toString()
                binding.std.text = it.countStudents.toString()
                binding.image.setImageURI(Uri.parse(it.imageUrl?:""))
            }else{
                closeView()
            }
        }
        vm.statePost.observe(viewLifecycleOwner){
            if (it == true){
                closeView()
            }
        }
    }

    fun openView(){
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.open_view)
        binding.cardView.startAnimation(anim)
    }

    fun closeView(){
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.close_view)
        anim.setAnimationListener(object: AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                requireActivity().supportFragmentManager.popBackStack()
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.cardView.startAnimation(anim)
    }

}


class RemoveMyBookmarkViewModel(val context: Context): ViewModel(){
    val course = MutableLiveData(CoursesData())
    val statePost = MutableLiveData<Boolean?>()

    private lateinit var listener: RemoveBookmarkListener

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private fun getBookmark(id: String){
        coroutineScope.launch {
            val responseData = GetCourseByIdRepository().request(id)
            if(responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<CoursesData>(){}.type
                Log.i("swagger", responseData.body.toString())
                try{
                    course.postValue(gson.fromJson(responseData.body, type))
                }catch (e: Exception){
                    course.postValue(CoursesData())
                }
            }else{
                course.postValue(CoursesData())
            }
        }
    }

    fun remove() {
            coroutineScope.launch {
                val responseData = RemoveBookmarkRepository().request(course.value?.id?:"", GetAccessToken().execute(context)?:"")
                statePost.postValue(responseData?.response?.isSuccessful)
            }
        listener.remove(course.value?.id?:"")
    }

    fun setting(arguments: Bundle?, list: RemoveBookmarkListener) {
        if (arguments!=null){
            getBookmark(arguments.getString("id", null))
        }
        listener = list
    }

}

class RemoveMyBookmarkViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoveMyBookmarkViewModel(context = context) as T
    }
}