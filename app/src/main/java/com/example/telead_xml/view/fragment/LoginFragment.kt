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
import com.example.telead_xml.data.repository.auth.SignInRepository
import com.example.telead_xml.data.shared_pref.SaveAccessToken
import com.example.telead_xml.data.shared_pref.SaveEmail
import com.example.telead_xml.data.shared_pref.SaveRefreshToken
import com.example.telead_xml.databinding.FragmentLoginBinding
import com.example.telead_xml.domen.objects.LoginData
import com.example.telead_xml.domen.objects.TokenData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var vm: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, LoginViewModelFactory(requireContext()))[LoginViewModel::class.java]
        setting()
        subscriptions()
        return binding.root
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
                        .add(R.id.login_fragment_container, Congratulations2Fragment())
                        .addToBackStack("congratulation2")
                        .commit()
                }
                400 -> {
                    Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                }
                404 -> {
                    Toast.makeText(requireContext(), "Email не зарегистрирован", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setting() {
        binding.registartion.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, RegistrationNowFragment())
                .addToBackStack("registration")
                .commit()
        }
        binding.login.setOnClickListener {
            vm.postData()
        }

        binding.email.addTextChangedListener {
            vm.redactEmail(it.toString())
        }
        binding.password.addTextChangedListener {
            vm.redactPassword(it.toString())
        }

        binding.forgotPass.setOnClickListener {
            vm.saveEmail()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, ForgotPasswordFragment())
                .addToBackStack("forgotPassword")
                .commit()
        }
    }
}

class LoginViewModel(val context: Context): ViewModel(){
    val data = MutableLiveData(LoginData())
    val statePost = MutableLiveData<Int?>()

    private val signInRepository = SignInRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun redactEmail(txt: String){
        data.value?.email = txt
    }
    fun redactPassword(txt: String){
        data.value?.password = txt
    }

    fun saveEmail(){
        SaveEmail().execute(data.value?.email.toString(), context)
    }
    fun postData(){
        coroutineScope.launch {
            val responseDate = signInRepository.request(data.value!!)
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

class LoginViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context = context) as T
    }
}