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
import com.example.telead_xml.databinding.FragmentCallsBinding
import com.example.telead_xml.domen.objects.CallData
import com.example.telead_xml.view.adapter.CallsAdapter
import com.example.telead_xml.view.listener.CallListener

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
        vm.setting()
        return binding.root
    }

    private fun setting() {
    }

    private fun subscription() {
        vm.callsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.callsRv.adapter = CallsAdapter(it, object: CallListener{
                    override fun call(id: String) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val fragment = IndoxCallsVoiceCallFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("callsVoice")
                            .commit()
                    }
                })
            }
        }
    }
}


class CallsViewModel(val context: Context): ViewModel(){
    val callsList = MutableLiveData(ArrayList<CallData>())

    private fun getCalls(){
        callsList.value = ArrayList()
        callsList.value?.add(CallData("Елена Карпова", "Нояб 04, 2023", "Исходящий"))
        callsList.value?.add(CallData("Николай Святой", "Янв 13, 2023", "Входящий"))

        callsList.value = callsList.value
    }

    fun setting() {
        getCalls()
    }
}

class CallsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CallsViewModel(context = context) as T
    }
}