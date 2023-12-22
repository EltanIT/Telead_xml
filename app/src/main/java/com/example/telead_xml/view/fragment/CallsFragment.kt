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
import com.example.telead_xml.databinding.FragmentCallsBinding
import com.example.telead_xml.domen.objects.CallData
import com.example.telead_xml.view.adapter.CallsAdapter

class CallsFragment : Fragment() {

    private lateinit var binding: FragmentCallsBinding
    private lateinit var vm: CallsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, CallsViewModelFactory(requireContext()))[CallsViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.callsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.callsRv.adapter = CallsAdapter(it)
            }
        }
    }

}


class CallsViewModel(val context: Context): ViewModel(){
    val callsList = MutableLiveData(ArrayList<CallData>())

    private fun getHistory(){
        callsList.value?.add(CallData("Patricia D. Regalado", "Нояб 04, 202х", "Исходящий", ""))

        callsList.value = callsList.value
    }


    fun setting() {
        getHistory()
    }
}

class CallsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CallsViewModel(context = context) as T
    }
}