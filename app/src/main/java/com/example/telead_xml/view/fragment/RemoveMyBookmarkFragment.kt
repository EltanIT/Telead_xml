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
import com.example.telead_xml.databinding.FragmentRemoveMyBookmarkBinding
import com.example.telead_xml.domen.objects.CoursesData

class RemoveMyBookmarkFragment() : Fragment() {

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
        vm.getBundle(arguments)
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {

    }

}


class RemoveMyBookmarkViewModel(val context: Context): ViewModel(){
    val course = MutableLiveData(CoursesData())


    fun getBundle(bundle: Bundle?){
        if (bundle!=null){
            val position = bundle.getInt("id")
        }
    }

}

class RemoveMyBookmarkViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoveMyBookmarkViewModel(context = context) as T
    }
}