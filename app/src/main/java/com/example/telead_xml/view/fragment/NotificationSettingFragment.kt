package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.data.shared_pref.GetNotificationSettings
import com.example.telead_xml.data.shared_pref.SaveNotificationSettings
import com.example.telead_xml.databinding.FragmentNotificationSettingBinding
import com.example.telead_xml.domen.objects.SettingNotificationData
import com.example.telead_xml.view.adapter.NotificationSettingAdapter
import com.example.telead_xml.view.listener.SettingNotificationListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationSettingFragment : Fragment() {

    private lateinit var binding: FragmentNotificationSettingBinding
    private lateinit var vm: NotificationSettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationSettingBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, NotificationSettingViewModelFactory(requireContext()))[NotificationSettingViewModel::class.java]
        setting()
        subscriptions()
        vm.getSetting()
        return binding.root
    }

    private fun subscriptions() {

        vm.settingList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.notificationsRv.adapter = NotificationSettingAdapter(it, object: SettingNotificationListener{
                    override fun check(settingNotificationData: SettingNotificationData) {
                        vm.redactConfig(settingNotificationData)
                    }

                })
            }else{
                vm.getDefaultSetting()
            }
        }
    }

    private fun setting() {
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}


class NotificationSettingViewModel(val context: Context): ViewModel(){
    val settingList = MutableLiveData<ArrayList<SettingNotificationData>>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

   fun getSetting(){
       coroutineScope.launch {
           val settingString = GetNotificationSettings().execute(context)
           val gson = Gson()
           val type = object : TypeToken<ArrayList<SettingNotificationData>>() {}.type
           settingList.postValue(gson.fromJson(settingString, type))
       }
   }

    fun getDefaultSetting() {
        settingList.value = ArrayList()
        settingList.value?.add(SettingNotificationData("specialOffer", false))
        settingList.value?.add(SettingNotificationData("sound", false))
        settingList.value?.add(SettingNotificationData("vibrate", false))
        settingList.value?.add(SettingNotificationData("generalNotification", false))
        settingList.value?.add(SettingNotificationData("promoDiscount", false))
        settingList.value?.add(SettingNotificationData("paymentOptions", false))
        settingList.value?.add(SettingNotificationData("appUpdate", false))
        settingList.value?.add(SettingNotificationData("newServiceAvailable", false))
        settingList.value?.add(SettingNotificationData("newTipsAvailable", false))

        settingList.value = settingList.value
    }

    fun redactConfig(settingNotificationData: SettingNotificationData) {
        val item = settingList.value?.filter { s -> s.name==settingNotificationData.name}?.single()
        val index = settingList.value?.indexOf(item)
        if (index != null) {
            settingList.value?.set(index, settingNotificationData)
            val gson = Gson()
            SaveNotificationSettings().execute(gson.toJson(settingList.value), context)
        }
    }
}

class NotificationSettingViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationSettingViewModel(context = context) as T
    }
}