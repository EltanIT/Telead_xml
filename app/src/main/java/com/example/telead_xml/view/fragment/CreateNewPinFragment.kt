package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentCreateNewPinBinding

class CreateNewPinFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPinBinding
    private lateinit var vm: CreateNewPinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPinBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, CreateNewPinViewModelFactory(requireContext()))[CreateNewPinViewModel::class.java]
        setting()
        subscriptions()
        return binding.root
    }

    private fun subscriptions() {
        vm.pinCode.observe(viewLifecycleOwner){
            if (it != null){
//                binding.pinCode.setText(it)
            }
        }

        vm.statePost.observe(viewLifecycleOwner){
            if (it){
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.login_fragment_container, SetYourFingerPrintFragment())
                    .addToBackStack("setYourFingerPrint")
                    .commit()
            }
            else{
                Toast.makeText(requireContext(), "Неверный пинкод", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setting() {
        binding.pinCode.addTextChangedListener {
            vm.redactCode(it.toString())
        }

        binding.next.setOnClickListener {
            vm.postData()
        }
    }
}



class CreateNewPinViewModel(val context: Context): ViewModel(){
    val pinCode = MutableLiveData("")
    val statePost = MutableLiveData<Boolean>()

    fun postData(){
        statePost.postValue(pinCode.value?.length == 4)
    }

    fun redactCode(txt: String){
        pinCode.value = txt
    }

}

class CreateNewPinViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateNewPinViewModel(context = context) as T
    }
}