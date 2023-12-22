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
import com.example.telead_xml.data.repository.auth.SignUpRepository
import com.example.telead_xml.data.shared_pref.SaveAccessToken
import com.example.telead_xml.data.shared_pref.SaveEmail
import com.example.telead_xml.data.shared_pref.SaveRefreshToken
import com.example.telead_xml.databinding.FragmentRegistrationNowBinding
import com.example.telead_xml.domen.objects.RegistrationData
import com.example.telead_xml.domen.objects.TokenData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegistrationNowFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationNowBinding
    private lateinit var vm: RegistrationNowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationNowBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, RegistrationNowViewModelFactory(requireActivity()))[RegistrationNowViewModel::class.java]
        setting()
        subscriptions()
        return binding.root
    }

    private fun setting() {

        binding.login.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, LoginFragment())
                .addToBackStack("login")
                .commit()
        }
        binding.registration.setOnClickListener {
            vm.postData()
        }

        binding.email.addTextChangedListener {
            vm.redactEmail(it.toString())
        }
        binding.password.addTextChangedListener {
            vm.redactPassword(it.toString())
        }
    }

    private fun subscriptions() {
        vm.data.observe(viewLifecycleOwner){
            if (it != null){
                binding.email.setText(it.email)
                binding.password.setText(it.password)
            }
        }


        vm.statePost.observe(viewLifecycleOwner){
            when (it) {
                200 -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.login_fragment_container, FillYourProfileFragment())
                        .addToBackStack("fillYourProfile")
                        .commit()
                }
                400 -> {
                    Toast.makeText(requireContext(), "Ошибка, пропробуйте позже", Toast.LENGTH_SHORT).show()
                }
                409 -> {
                    Toast.makeText(requireContext(), "Почта уже существует", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}


class RegistrationNowViewModel(val context: Context): ViewModel(){
    val data = MutableLiveData(RegistrationData())
    val statePost = MutableLiveData<Int?>()

    private val signUpRepository = SignUpRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun redactEmail(txt: String){
        data.value?.email = txt
    }
    fun redactPassword(txt: String){
        data.value?.password = txt
    }

    fun postData(){
        coroutineScope.launch {
            val responseDate = signUpRepository.request(data.value!!)
            if (responseDate!=null){
                if (responseDate.response?.code == 200){
                    val jsonObject = responseDate.body?.let { JSONObject(it) }
                    val refreshToken = jsonObject?.getString("refreshToken")
                    val accessToken = jsonObject?.getString("accessToken")
                    val tokenData: TokenData = TokenData(accessToken, refreshToken)
                    SaveRefreshToken().execute(tokenData.refreshToken?:"", context)
                    SaveAccessToken().execute(tokenData.accessToken?:"", context)
                    SaveEmail().execute(data.value?.email ?: "", context)
                }
                statePost.postValue(responseDate.response?.code)
            }else{
                statePost.postValue(null)
            }
        }
    }



}

class RegistrationNowViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrationNowViewModel(context = context) as T
    }
}