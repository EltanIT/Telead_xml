package com.example.telead_xml.view.fragment

import android.app.Activity
import android.app.DatePickerDialog
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
import com.example.telead_xml.data.repository.profile.PutProfileRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.data.shared_pref.GetEmail
import com.example.telead_xml.databinding.FragmentFillYourProfileBinding
import com.example.telead_xml.databinding.FragmentProfilesBinding
import com.example.telead_xml.domen.objects.DobData
import com.example.telead_xml.domen.objects.ProfileData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.URI
import java.util.Calendar

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
        vm.data.observe(viewLifecycleOwner){
            if (it!=null){
                binding.name.text = it.fullname
                binding.email.text = it.email
                binding.image.setImageURI(Uri.parse(it.urlIcon))
            }
        }
    }

    private fun setting() {
        binding.imageClick.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_VIEW
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivityForResult(intent, Consts().OPEN_GALLERY)
        }

        binding.editProfile.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.full_home_container_view, EditProfilesFragment())
                .addToBackStack("editProfiles")
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Consts().OPEN_GALLERY && data != null){
            val uri = data.data
            if (uri!=null){
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
   val data = MutableLiveData<ProfileData?>()
   val statePost = MutableLiveData<Int?>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val getProfileRepository = GetProfileRepository()

    fun getData(){
        coroutineScope.launch {
            val responseData = getProfileRepository.request(GetAccessToken().execute(context)?:"")
            if (responseData!=null){
                val gson = Gson()
                val profile: ProfileData? = gson.fromJson(responseData.body, ProfileData::class.java)
                data.postValue(profile)
                statePost.postValue(responseData.response?.code)
            }
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