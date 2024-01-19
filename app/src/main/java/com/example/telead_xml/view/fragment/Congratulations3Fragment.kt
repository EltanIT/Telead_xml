package com.example.telead_xml.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCourseByIdRepository
import com.example.telead_xml.databinding.FragmentCongratulations3Binding
import com.example.telead_xml.domen.objects.CardData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.activity.HomeActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Congratulations3Fragment : Fragment() {

    private lateinit var binding: FragmentCongratulations3Binding
    private lateinit var vm: Congratulation3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratulations3Binding.inflate(layoutInflater)
        vm = ViewModelProvider(this, Congratulation3ViewModelFactory(requireContext()))[Congratulation3ViewModel::class.java]
        setting()
        return binding.root
    }

    fun setting(){
        binding.courses.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}

class Congratulation3ViewModel(val context: Context): ViewModel(){

}

class Congratulation3ViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Congratulation3ViewModel(context = context) as T
    }
}