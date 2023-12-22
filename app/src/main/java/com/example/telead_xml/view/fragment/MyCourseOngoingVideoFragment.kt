package com.example.telead_xml.view.fragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.databinding.FragmentMyCourseOngoingVideoBinding
import java.net.URL

class MyCourseOngoingVideoFragment : Fragment() {
    private lateinit var binding: FragmentMyCourseOngoingVideoBinding
    private lateinit var vm: MyCourseOngoingVideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCourseOngoingVideoBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyCourseOngoingVideoViewModelFactory(requireContext()))[MyCourseOngoingVideoViewModel::class.java]
        subscription()
        setting()
        vm.setting(arguments)
        return binding.root
    }

    private fun subscription() {
        vm.video.observe(viewLifecycleOwner){
            if (it!=null){
                binding.playVideo.setVideoURI(it)
                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(binding.playVideo)
                binding.playVideo.setMediaController(mediaController)
            }
            else{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun setting() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}


class MyCourseOngoingVideoViewModel(val context: Context): ViewModel(){
    val video = MutableLiveData<Uri?>(null)

    fun setting(bundle: Bundle?) {
        if (bundle!=null){
            video.value = Uri.parse(bundle.getString("videoUri", null))
        }
    }
}

class MyCourseOngoingVideoViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyCourseOngoingVideoViewModel(context = context) as T
    }
}