package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.auth.CodeRecoveryRepository
import com.example.telead_xml.data.shared_pref.GetEmail
import com.example.telead_xml.data.shared_pref.SaveAccessToken
import com.example.telead_xml.data.shared_pref.SaveEmail
import com.example.telead_xml.data.shared_pref.SaveRefreshToken
import com.example.telead_xml.databinding.FragmentForgotPasswordBinding
import com.example.telead_xml.domen.objects.ForgotPasswordData
import com.example.telead_xml.domen.objects.TokenData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var vm: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, ForgotPasswordViewModelFactory(requireActivity()))[ForgotPasswordViewModel::class.java]
        setting()
        subscriptions()
        vm.getEmail()
        return binding.root
    }

    private fun setting() {
        binding.emailBtn.setOnClickListener {
            binding.emailBtn.isEnabled = false
            vm.postData()
        }
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun subscriptions() {
        vm.data.observe(viewLifecycleOwner){
            if (it != null){
                binding.email.text = it.email
                binding.phone.text = it.phone
            }
        }

        vm.statePost.observe(viewLifecycleOwner){
            when (it) {
                200 -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.login_fragment_container, VerifyForgotPasswordFragment())
                        .addToBackStack("verifyForgotPassword")
                        .commit()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                    binding.emailBtn.isEnabled = true
                }
            }
        }
    }
}


class ForgotPasswordViewModel(val context: Context): ViewModel(){
    val data = MutableLiveData(ForgotPasswordData("", ""))
    val statePost = MutableLiveData<Int?>()

    private val codeRecoveryRepository = CodeRecoveryRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun postData(){
        coroutineScope.launch {
            val responseData = codeRecoveryRepository.request(data.value!!.email)
            statePost.postValue(responseData?.response?.code)
        }
    }

    fun getEmail() {
        data.value?.email = GetEmail().execute(context).toString()
    }

}

class ForgotPasswordViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForgotPasswordViewModel(context = context) as T
    }
}