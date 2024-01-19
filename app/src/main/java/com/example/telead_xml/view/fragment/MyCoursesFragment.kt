package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentMyCoursesBinding
import com.example.telead_xml.domen.objects.SearchHistoryData

class MyCoursesFragment : Fragment() {

    private lateinit var binding: FragmentMyCoursesBinding
    private lateinit var vm: MyCoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCoursesBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, MyCoursesViewModelFactory(requireContext()))[MyCoursesViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {
        binding.completed.isSelected = true
        redactButton()
        val adapter = com.example.telead_xml.view.adapter.FragmentAdapter(FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, requireActivity().supportFragmentManager)
        adapter.addFrag(MyCompleteCoursesListFragment())
        adapter.addFrag(MyOngoingCoursesListFragment())
        binding.viewpager.adapter = adapter

        binding.ongoing.setOnClickListener {
            binding.viewpager.setCurrentItem(1, true)
            binding.nameCategory.text = "Наставники"
            binding.completed.isSelected = false
            binding.ongoing.isSelected = true
            redactButton()
        }
        binding.completed.setOnClickListener {
            binding.viewpager.setCurrentItem(0, true)
            binding.nameCategory.text = "Онлайн Курсы"
            binding.completed.isSelected = true
            binding.ongoing.isSelected = false
            redactButton()
        }

        binding.viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position==0){
                    binding.nameCategory.text = "Завершенные"
                    binding.completed.isSelected = true
                    binding.ongoing.isSelected = false
                    redactButton()
                }else{
                    binding.nameCategory.text = "Текущие"
                    binding.completed.isSelected = false
                    binding.ongoing.isSelected = true
                    redactButton()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun redactButton(){
        if (binding.completed.isSelected){
            binding.completed.setTextColor(resources.getColor(R.color.white))
            binding.ongoing.setTextColor(resources.getColor(R.color.category_btn_text_unselected_color))
        }else{
            binding.ongoing.setTextColor(resources.getColor(R.color.white))
            binding.completed.setTextColor(resources.getColor(R.color.category_btn_text_unselected_color))
        }
    }

    private fun subscription() {

    }

}


class MyCoursesViewModel(val context: Context): ViewModel(){
    val historyList = MutableLiveData(ArrayList<SearchHistoryData>())

    private fun getHistory(){
        historyList.value?.add(SearchHistoryData("3D design"))
        historyList.value?.add(SearchHistoryData("3D design"))
        historyList.value?.add(SearchHistoryData("3D design"))
        historyList.value?.add(SearchHistoryData("3D design"))
        historyList.value?.add(SearchHistoryData("3D design"))

        historyList.value = historyList.value
    }


    fun setting() {
        getHistory()
    }
}

class MyCoursesViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyCoursesViewModel(context = context) as T
    }
}