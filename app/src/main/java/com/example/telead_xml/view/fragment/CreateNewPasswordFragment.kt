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
import com.example.telead_xml.data.repository.auth.ResetPasswordRepository
import com.example.telead_xml.data.shared_pref.GetEmail
import com.example.telead_xml.databinding.FragmentCreateNewPasswordBinding
import com.example.telead_xml.domen.objects.ResetPasswordData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPasswordBinding
    private lateinit var vm: CreateNewPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPasswordBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, CreateNewPasswordViewModelFactory(requireActivity()))[CreateNewPasswordViewModel::class.java]
        setting()
        subscriptions()
        vm.getData(arguments)
        return binding.root
    }

    private fun setting() {
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.next.setOnClickListener {
            if (binding.password.text.toString().equals(binding.replayPassword.text.toString())){
                vm.postData()
            }
        }

        binding.password.addTextChangedListener {
            vm.redactPassword(it.toString())
        }
    }

    private fun subscriptions() {
        vm.data.observe(viewLifecycleOwner){
            if (it != null){
                binding.password.setText(it.newPassword)
                binding.replayPassword.setText(it.newPassword)
            }
        }

        vm.statePost.observe(viewLifecycleOwner){
            when (it) {
                200 -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.login_fragment_container, Congratulations2Fragment())
                        .addToBackStack("congratulation2")
                        .commit()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


class CreateNewPasswordViewModel(val context: Context): ViewModel(){
    val data = MutableLiveData(ResetPasswordData(GetEmail().execute(context) ?: "","", ""))
    val statePost = MutableLiveData<Int?>()

    private val resetPasswordRepository = ResetPasswordRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun postData(){
        coroutineScope.launch {
            val responseData = resetPasswordRepository.request(data.value!!)
            statePost.postValue(responseData?.response?.code)
        }
    }

    fun getData(bundle: Bundle?) {
        if (bundle!=null){
            data.value?.recoveryCode = bundle.getString("code", "")
        }
    }

    fun redactPassword(txt: String) {
        data.value?.newPassword = txt
    }

}

class CreateNewPasswordViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateNewPasswordViewModel(context = context) as T
    }
}