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
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentSearchResultListBinding
import com.example.telead_xml.domen.objects.RequestData
import com.example.telead_xml.view.adapter.IntroAdapter

class SearchResultListFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultListBinding
    private lateinit var vm: SearchResultListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultListBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, SearchResultListViewModelFactory(requireContext()))[SearchResultListViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {
        val adapter =  IntroAdapter(FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, requireActivity().supportFragmentManager)
        adapter.addFrag(CoursesListFragment(vm.getRequest()?:""))
        adapter.addFrag(MentorsListFragment(vm.getRequest()?:""))
        binding.viewpager.adapter = adapter

        binding.mentors.setOnClickListener {
            binding.viewpager.setCurrentItem(1, true)
            binding.nameCategory.text = "Наставники"
            binding.courses.isSelected = false
            binding.mentors.isSelected = true
            redactButton()
        }
        binding.courses.setOnClickListener {
            binding.viewpager.setCurrentItem(0, true)
            binding.nameCategory.text = "Онлайн Курсы"
            binding.courses.isSelected = true
            binding.mentors.isSelected = false
            redactButton()
        }

        binding.viewpager.addOnPageChangeListener(object: OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position==0){
                    binding.nameCategory.text = "Онлайн Курсы"
                    binding.courses.isSelected = true
                    binding.mentors.isSelected = false
                    redactButton()
                }else{
                    binding.nameCategory.text = "Наставники"
                    binding.courses.isSelected = false
                    binding.mentors.isSelected = true
                    redactButton()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun redactButton(){
        if (binding.courses.isSelected){
            binding.courses.setTextColor(resources.getColor(R.color.white))
            binding.mentors.setTextColor(resources.getColor(R.color.category_btn_text_unselected_color))
        }else{
            binding.mentors.setTextColor(resources.getColor(R.color.white))
            binding.courses.setTextColor(resources.getColor(R.color.category_btn_text_unselected_color))
        }
    }

    private fun subscription() {

    }
}


class SearchResultListViewModel(val context: Context): ViewModel(){
    val requestData = MutableLiveData(RequestData())

    private fun getHistory(){

    }


    fun setting() {
        getHistory()
    }

    fun getRequest(): String? {
        return requestData.value?.request
    }
}

class SearchResultListViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchResultListViewModel(context = context) as T
    }
}