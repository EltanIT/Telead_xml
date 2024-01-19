package com.example.telead_xml.view.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.config.Consts
import com.example.telead_xml.data.repository.profile.PutProfileRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.data.shared_pref.GetEmail
import com.example.telead_xml.databinding.FragmentFillYourProfileBinding
import com.example.telead_xml.domen.objects.DobData
import com.example.telead_xml.domen.objects.ProfileData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class FillYourProfileFragment : Fragment() {

    private lateinit var binding: FragmentFillYourProfileBinding
    private lateinit var vm: FillYourProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFillYourProfileBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, FillYourProfileViewModelFactory(requireContext()))[FillYourProfileViewModel::class.java]
        setting()
        subscriptions()
        return binding.root
    }

    private fun setting() {
//        binding.next.isEnabled = false
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

        binding.gender.onItemSelectedListener = object : OnItemSelectedListener{
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

        binding.next.setOnClickListener {
            vm.postData()
        }
        binding.iconProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, Consts().OPEN_GALLERY)
        }
    }

    private fun subscriptions() {
//        vm.checkEt.observe(viewLifecycleOwner){
//            binding.next.isEnabled = it
//        }

        vm.dob.observe(viewLifecycleOwner){
            if (it!=null){
                binding.birthday.text = "${it.year}-${it.month}-${it.day}"
            }
        }

        vm.data.observe(viewLifecycleOwner){
            if (it != null){
                binding.name.setText(it.fullName)
                binding.nickName.setText(it.nickname)
                binding.email.setText(it.email)
                binding.phone.setText(it.phone)
                binding.gender.setSelection(Consts().genderList.indexOf(it.gender))
            }
        }

        vm.statePost.observe(viewLifecycleOwner){

            when (it) {
                200 -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.login_fragment_container, CreateNewPinFragment())
                        .addToBackStack("createNewPin")
                        .commit()
                }
                409 -> {
                    Toast.makeText(requireContext(), "Номер занят", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.login_fragment_container, CreateNewPinFragment())
                        .addToBackStack("createNewPin")
                        .commit()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Consts().OPEN_GALLERY && data !=null){
            binding.iconProfile.setImageURI(data.data ?: Uri.parse(""))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}


class FillYourProfileViewModel(val context: Context): ViewModel(){
    val data = MutableLiveData(ProfileData(email = GetEmail().execute(context)?:""))
    val dob = MutableLiveData(DobData(
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))
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
        data.value?.fullName = txt
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
            if (responseDate?.response?.isSuccessful == true){
                statePost.postValue(responseDate.response.code)
            }else{
                statePost.postValue(null)
            }
        }
    }

    private fun checkValue() {
        checkEt.value = (data.value?.fullName?.isNotEmpty() == true
                && data.value?.nickname?.isNotEmpty() == true
                && data.value?.dob?.isNotEmpty() == true
                && data.value?.email?.isNotEmpty() == true
                && data.value?.phone?.isNotEmpty() == true
                && data.value?.gender?.isNotEmpty() == true)
    }
}

class FillYourProfileViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FillYourProfileViewModel(context = context) as T
    }
}