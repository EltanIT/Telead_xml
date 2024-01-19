package com.example.telead_xml.view.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.config.Consts
import com.example.telead_xml.data.repository.profile.GetProfileRepository
import com.example.telead_xml.data.repository.profile.PutProfileRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.databinding.FragmentEditProfilesBinding
import com.example.telead_xml.domen.objects.DobData
import com.example.telead_xml.domen.objects.ProfileData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class EditProfilesFragment : Fragment() {

    private lateinit var binding: FragmentEditProfilesBinding
    private lateinit var vm: EditProfilesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfilesBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, EditProfilesViewModelFactory(requireContext()))[EditProfilesViewModel::class.java]
        setting()
        subscriptions()
        vm.getData()
        return binding.root
    }

    private fun subscriptions() {
        vm.dob.observe(viewLifecycleOwner){
            if (it!=null){
                binding.birthday.text = "${it.year}-${it.month}-${it.day}"
            }
        }

        vm.profile.observe(viewLifecycleOwner){
            if (it != null){
                binding.name.setText(it.fullName)
                binding.nickName.setText(it.nickname)
                binding.email.setText(it.email)
                binding.phone.setText(it.phone)
                binding.birthday.text = it.dob
                binding.gender.setSelection(Consts().genderList.indexOf(it.gender))
            }
        }

        vm.statePost.observe(viewLifecycleOwner){
            when (it) {
                200 -> {
                    Toast.makeText(requireContext(), "Изменения внесены", Toast.LENGTH_SHORT).show()
                }
                409 -> {
                    Toast.makeText(requireContext(), "Номер занят", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                }
            }
        }
        vm.stateGet.observe(viewLifecycleOwner){
            when (it) {
                200 -> {

                }
                404 -> {
                    Toast.makeText(requireContext(), "Не найдено", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setting() {
        binding.gender.adapter = ArrayAdapter(requireContext(), com.chaos.view.R.layout.support_simple_spinner_dropdown_item, Consts().genderList)

        binding.email.addTextChangedListener{
            vm.redactEmail(it.toString())
        }
        binding.nickName.addTextChangedListener{
            vm.redactNickName(it.toString())
        }
        binding.name.addTextChangedListener{
            vm.redactName(it.toString())
        }
        binding.birthdayClick.setOnClickListener{
            vm.redactBirthday()
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vm.redactGender(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.phone.addTextChangedListener{
            vm.redactPhone(it.toString())
        }

        binding.update.setOnClickListener {
            vm.postData()
        }
    }

}


class EditProfilesViewModel(val context: Context): ViewModel(){
    val profile = MutableLiveData<ProfileData?>()
    val dob = MutableLiveData(
        DobData(
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
    )
    val statePost = MutableLiveData<Int?>()
    val stateGet = MutableLiveData<Int?>()

    private val putProfileRepository = PutProfileRepository()
    private val getProfileRepository = GetProfileRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun postData(){
        coroutineScope.launch {
            val responseDate = putProfileRepository.request(profile.value!!, GetAccessToken().execute(context)?:"")
            statePost.postValue(responseDate?.response?.code)
        }
    }
    fun getData(){
        coroutineScope.launch {
            val responseDate = getProfileRepository.request(GetAccessToken().execute(context)?:"")
            if (responseDate?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ProfileData>(){}.type
                val p = gson.fromJson(responseDate.body, type) as ProfileData
                profile.postValue(p)
            }
            stateGet.postValue(responseDate?.response?.code)
        }
    }



    fun redactEmail(txt: String){
        profile.value?.email = txt
    }
    fun redactNickName(txt: String){
        profile.value?.nickname = txt
    }
    fun redactName(txt: String){
        profile.value?.fullName = txt
    }
    fun redactBirthday(){
        val dpd = DatePickerDialog(context, { view, year, monthOfYear, dayOfMonth ->
            profile.value?.dob = ("$year-$monthOfYear-$dayOfMonth")
            dob.value = DobData(year, monthOfYear, dayOfMonth)
        }, dob.value!!.year, dob.value!!.month, dob.value!!.day)
        dpd.show()
    }
    fun redactPhone(txt: String){
        profile.value?.phone = txt
    }
    fun redactGender(pos: Int){
        if (pos > 0){
            profile.value?.gender = Consts().genderList[pos]
        }else{
            profile.value?.gender = ""
        }
    }



}

class EditProfilesViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditProfilesViewModel(context = context) as T
    }
}