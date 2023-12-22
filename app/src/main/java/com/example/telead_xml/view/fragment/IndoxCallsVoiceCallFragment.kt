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
import com.example.telead_xml.databinding.FragmentIndoxCallsVoiceCallBinding
import com.example.telead_xml.domen.objects.ChatData

class IndoxCallsVoiceCallFragment : Fragment() {

    private lateinit var binding: FragmentIndoxCallsVoiceCallBinding
    private lateinit var vm: IndoxCallsVoiceCallViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIndoxCallsVoiceCallBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, IndoxCallsVoiceCallViewModelFactory(requireContext()))[IndoxCallsVoiceCallViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {

    }

}

class IndoxCallsVoiceCallViewModel(val context: Context): ViewModel(){


    fun setting() {

    }
}

class IndoxCallsVoiceCallViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IndoxCallsVoiceCallViewModel(context = context) as T
    }
}