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
import com.example.telead_xml.domen.objects.LessonData
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyCourseOngoingVideoFragment : Fragment() {
    private lateinit var binding: FragmentMyCourseOngoingVideoBinding
    private lateinit var vm: MyCourseOngoingVideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding = FragmentMyCourseOngoingVideoBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyCourseOngoingVideoViewModelFactory(requireContext()))[MyCourseOngoingVideoViewModel::class.java]
        subscription()
        setting()
        vm.setting(arguments)
        return binding.root
    }

    private fun subscription() {
        vm.lesson.observe(viewLifecycleOwner){
            if (it!=null){
                Picasso.with(requireContext())
                binding.playVideo.setVideoPath("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(binding.playVideo)
                mediaController.setMediaPlayer(binding.playVideo)
                binding.playVideo.setMediaController(mediaController)
                binding.playVideo.start()
            }
            else{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    private fun setting() {
        binding.backView.setOnClickListener {
            binding.playVideo.performClick()
        }
    }
}


class MyCourseOngoingVideoViewModel(val context: Context): ViewModel(){
    val lesson = MutableLiveData(LessonData())

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun setting(bundle: Bundle?) {
        if (bundle!=null){
            val id = Uri.parse(bundle.getString("id", ""))
            coroutineScope.launch {

            }
            lesson.value = lesson.value
        }
    }
}

class MyCourseOngoingVideoViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyCourseOngoingVideoViewModel(context = context) as T
    }
}