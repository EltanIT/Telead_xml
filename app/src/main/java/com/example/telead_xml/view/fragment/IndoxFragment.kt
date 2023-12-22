package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentIndoxBinding
import com.example.telead_xml.domen.objects.SearchHistoryData
import com.example.telead_xml.view.adapter.IntroAdapter

class IndoxFragment : Fragment() {

    private lateinit var binding: FragmentIndoxBinding
    private lateinit var vm: IndoxViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIndoxBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, IndoxViewModelFactory(requireContext()))[IndoxViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {
        binding.chats.isSelected = true
        redactButton()
        val adapter =  IntroAdapter(FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, requireActivity().supportFragmentManager)
        adapter.addFrag(ChatsFragment())
        adapter.addFrag(CallsFragment())
        binding.viewPager.adapter = adapter

        binding.calls.setOnClickListener {
            binding.viewPager.setCurrentItem(1, true)
            binding.chats.isSelected = false
            binding.calls.isSelected = true
            redactButton()
        }
        binding.chats.setOnClickListener {
            binding.viewPager.setCurrentItem(0, true)
            binding.chats.isSelected = true
            binding.calls.isSelected = false
            redactButton()
        }

        binding.viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position==0){
                    binding.chats.isSelected = true
                    binding.calls.isSelected = false
                    redactButton()
                }else{
                    binding.chats.isSelected = false
                    binding.calls.isSelected = true
                    redactButton()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun redactButton(){
        if (binding.chats.isSelected){
            binding.chats.setTextColor(resources.getColor(R.color.white))
            binding.calls.setTextColor(resources.getColor(R.color.category_btn_text_unselected_color))
        }else{
            binding.calls.setTextColor(resources.getColor(R.color.white))
            binding.chats.setTextColor(resources.getColor(R.color.category_btn_text_unselected_color))
        }
    }

    private fun subscription() {

    }

}


class IndoxViewModel(val context: Context): ViewModel(){
    fun setting() {

    }
}

class IndoxViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IndoxViewModel(context = context) as T
    }
}