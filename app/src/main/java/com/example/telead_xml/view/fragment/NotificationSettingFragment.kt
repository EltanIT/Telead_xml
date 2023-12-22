package com.example.telead_xml.view.fragment

import android.app.DatePickerDialog
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
import com.example.telead_xml.config.Consts
import com.example.telead_xml.data.repository.profile.PutProfileRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.data.shared_pref.GetEmail
import com.example.telead_xml.databinding.FragmentFillYourProfileBinding
import com.example.telead_xml.databinding.FragmentNotificationBinding
import com.example.telead_xml.domen.objects.DobData
import com.example.telead_xml.domen.objects.ProfileData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class NotificationSettingFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var vm: NotificationSettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, NotificationSettingViewModelFactory(requireContext()))[NotificationSettingViewModel::class.java]
        setting()
        subscriptions()
        return binding.root
    }

    private fun subscriptions() {

    }

    private fun setting() {

    }

}


class NotificationSettingViewModel(val context: Context): ViewModel(){
    val data = MutableLiveData(ProfileData(email = GetEmail().execute(context)?:""))
    val dob = MutableLiveData(
        DobData(
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
    )
    val statePost = MutableLiveData<Int?>()
    val checkEt = MutableLiveData(false)

    private val putProfileRepository = PutProfileRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun redactEmail(txt: String){
        data.value?.email = txt
        checkValue()
    }
    fun redactNickName(txt: String){
        data.value?.nickname = txt
        checkValue()
    }
    fun redactName(txt: String){
        data.value?.fullname = txt
        checkValue()
    }
    fun redactBirthday(){
        val dpd = DatePickerDialog(context, { view, year, monthOfYear, dayOfMonth ->
            data.value?.dob = ("$year-$monthOfYear-$dayOfMonth")
            dob.value = DobData(year, monthOfYear, dayOfMonth)
        }, dob.value!!.year, dob.value!!.month, dob.value!!.day)
        dpd.show()
        checkValue()
    }
    fun redactPhone(txt: String){
        data.value?.phone = txt
        checkValue()
    }
    fun redactGender(pos: Int){
        if (pos > 0){
            data.value?.gender = Consts().genderList[pos]
        }else{
            data.value?.gender = ""
        }
        checkValue()
    }

    fun postData(){
        coroutineScope.launch {
            val responseDate = putProfileRepository.request(data.value!!, GetAccessToken().execute(context)?:"")
            if (responseDate!=null){
                statePost.postValue(responseDate.response?.code)
            }else{
                statePost.postValue(null)
            }
        }
    }

    private fun checkValue() {
        checkEt.value = (data.value?.fullname?.isNotEmpty() == true
                && data.value?.nickname?.isNotEmpty() == true
                && data.value?.dob?.isNotEmpty() == true
                && data.value?.email?.isNotEmpty() == true
                && data.value?.phone?.isNotEmpty() == true
                && data.value?.gender?.isNotEmpty() == true)
    }
}

class NotificationSettingViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationSettingViewModel(context = context) as T
    }
}