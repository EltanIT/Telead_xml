package com.example.telead_xml.view.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.example.telead_xml.data.repository.profile.GetProfileRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.databinding.FragmentProfilesBinding
import com.example.telead_xml.domen.objects.ProfileData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfilesFragment : Fragment() {

    private lateinit var binding: FragmentProfilesBinding
    private lateinit var vm: ProfilesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilesBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, ProfilesViewModelFactory(requireContext()))[ProfilesViewModel::class.java]
        setting()
        subscriptions()
        vm.getData()
        return binding.root
    }

    private fun subscriptions() {
        vm.profile.observe(viewLifecycleOwner){
            if (it!=null){
                binding.name.text = it.fullName
                binding.email.text = it.email
                binding.image.setImageURI(Uri.parse(it.urlIcon?:""))
            }
        }
    }

    private fun setting() {
        binding.imageClick.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, Consts().OPEN_GALLERY)
        }

        binding.editProfile.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.full_home_container_view, EditProfilesFragment())
                .addToBackStack("editProfiles")
                .commit()
        }
        binding.notification.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.full_home_container_view, NotificationSettingFragment())
                .addToBackStack("notificationsSettings")
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Consts().OPEN_GALLERY && data != null){
            val uri = data.data
            if (uri!=null){
                binding.image.setImageURI(uri)
                val os = ByteArrayOutputStream()
                val inputStream = requireActivity().contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()
                vm.redactImage(byteArray)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}


class ProfilesViewModel(val context: Context): ViewModel(){
   val profile = MutableLiveData<ProfileData?>()
   val statePost = MutableLiveData<Int?>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val getProfileRepository = GetProfileRepository()

    fun getData(){
        coroutineScope.launch {
            val responseData = getProfileRepository.request(GetAccessToken().execute(context)?:"")
            if (responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ProfileData>(){}.type
                val data: ProfileData? = gson.fromJson(responseData.body, type)
                profile.postValue(data)
                statePost.postValue(responseData.response?.code)
            }else{
                profile.postValue(ProfileData(email = "g@g.g", fullName = "Николай М."))
            }
            statePost.postValue(responseData?.response?.code)
        }
    }

    fun redactImage(byteArray: ByteArray?) {
        if (byteArray!=null){

        }
    }
}

class ProfilesViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfilesViewModel(context = context) as T
    }
}