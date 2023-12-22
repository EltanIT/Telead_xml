package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.telead_xml.data.repository.auth.CodeRecoveryRepository
import com.example.telead_xml.data.shared_pref.GetEmail
import com.example.telead_xml.databinding.FragmentVerifyForgotPasswordBinding
import com.example.telead_xml.domen.objects.CodeVerifyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VerifyForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentVerifyForgotPasswordBinding
    private lateinit var vm: VerifyForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyForgotPasswordBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, VerifyForgotPasswordViewModelFactory(requireActivity()))[VerifyForgotPasswordViewModel::class.java]
        setting()
        subscriptions()
        return binding.root
    }

    override fun onResume() {
        val timer = object: CountDownTimer(60000, 1000){
            override fun onTick(p0: Long) {
                binding.timer.text = (p0/1000).toString()
            }

            override fun onFinish() {

            }

        }
        timer.start()
        super.onResume()
    }

    private fun setting() {
        binding.next.setOnClickListener {
            vm.postData()
        }

        binding.code.addTextChangedListener {
            vm.redactCode(it.toString())
        }
    }

    private fun subscriptions() {
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        vm.data.observe(viewLifecycleOwner){
            if (it != null){
                binding.email.text = it.email
                binding.code.setText(it.recoveryCode)
            }
        }

        vm.statePost.observe(viewLifecycleOwner){
            when (it) {
                200 -> {
                    val bundle = Bundle()
                    bundle.putString("code", vm.data.value?.recoveryCode)
                    val fragment = CreateNewPasswordFragment()
                    fragment.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.login_fragment_container, fragment)
                        .addToBackStack("createNewPassword")
                        .commit()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


class VerifyForgotPasswordViewModel(val context: Context): ViewModel(){
    val data = MutableLiveData(CodeVerifyData(GetEmail().execute(context) ?: "",""))
    val statePost = MutableLiveData<Int?>()

    private val codeRecoveryRepository = CodeRecoveryRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun postData(){
        coroutineScope.launch {
            val responseData = codeRecoveryRepository.request(data.value!!.email)
            statePost.postValue(responseData?.response?.code)
        }
    }

    fun redactCode(txt: String) {
        data.value?.recoveryCode = txt
    }

}

class VerifyForgotPasswordViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VerifyForgotPasswordViewModel(context = context) as T
    }
}