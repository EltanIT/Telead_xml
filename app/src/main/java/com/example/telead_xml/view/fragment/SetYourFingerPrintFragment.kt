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
import com.example.telead_xml.databinding.FragmentSetYourFingerPrintBinding

class SetYourFingerPrintFragment : Fragment() {

    private lateinit var binding: FragmentSetYourFingerPrintBinding
    private lateinit var vm: SetYourFingerPrintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetYourFingerPrintBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, SetYourFingerPrintViewModelFactory(requireContext()))[SetYourFingerPrintViewModel::class.java]
        setting()
        subscriptions()
        return binding.root
    }

    private fun subscriptions() {
        vm.statePost.observe(viewLifecycleOwner){
            if (it){
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.login_fragment_container, Congratulations1Fragment())
                    .addToBackStack("congratulation")
                    .commit()
            }
        }
    }

    private fun setting() {
        binding.next.setOnClickListener {
            vm.postData()
        }
        binding.skip.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.login_fragment_container, Congratulations1Fragment())
                .addToBackStack("congratulation")
                .commit()
        }
    }

}

class SetYourFingerPrintViewModel(val context: Context): ViewModel(){
    val statePost = MutableLiveData<Boolean>()

    fun postData(){
        statePost.postValue(true)
    }

}

class SetYourFingerPrintViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SetYourFingerPrintViewModel(context = context) as T
    }
}