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
import com.example.telead_xml.databinding.FragmentMentorsListBinding
import com.example.telead_xml.domen.objects.MentorData
import com.example.telead_xml.view.adapter.TopMentorFullAdapter
import com.example.telead_xml.view.listener.MentorListener

class MentorsListFragment(s: String) : Fragment() {

    private lateinit var binding: FragmentMentorsListBinding
    private lateinit var vm: MentorsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorsListBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MentorsListViewModelFactory(requireContext()))[MentorsListViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }

    private fun setting() {

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


class MentorsListViewModel(val context: Context): ViewModel(){
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

class MentorsListViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MentorsListViewModel(context = context) as T
    }
}