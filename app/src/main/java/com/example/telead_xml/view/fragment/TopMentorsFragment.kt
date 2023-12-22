package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentTopMentorsBinding
import com.example.telead_xml.domen.objects.MentorData
import com.example.telead_xml.view.adapter.TopMentorFullAdapter
import com.example.telead_xml.view.listener.MentorListener

class TopMentorsFragment : Fragment() {

    private lateinit var binding: FragmentTopMentorsBinding
    private lateinit var vm: TopMentorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopMentorsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, TopMentorsViewModelFactory(requireContext()))[TopMentorsViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    override fun onResume() {
        vm.setting()
        super.onResume()
    }

    private fun setting() {
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun subscription() {
        vm.topMentorList.observe(viewLifecycleOwner){
            if (it != null){
                binding.mentorRv.adapter = TopMentorFullAdapter(it, object: MentorListener {
                    override fun click(position: Int) {
                        val bundle = Bundle()
                        bundle.putInt("id", position)
                        val fragment = SingleMentorsDetailsFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("singleMentorDetails")
                            .commit()
                    }

                })
            }
        }
    }

}


class TopMentorsViewModel(val context: Context): ViewModel(){
    val topMentorList = MutableLiveData(ArrayList<MentorData>())

    private fun getTopMentor(){
        topMentorList.value?.add(MentorData("Вадим", "William", "S", "3D Design", ""))
        topMentorList.value?.add(MentorData("Игорь", "William", "S", "3D Design", ""))
        topMentorList.value?.add(MentorData("Николай", "William", "S", "3D Design", ""))
        topMentorList.value?.add(MentorData("Виктория", "William", "S", "3D Design", ""))
        topMentorList.value = topMentorList.value
    }

    fun setting() {
        getTopMentor()
    }
}

class TopMentorsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopMentorsViewModel(context = context) as T
    }
}