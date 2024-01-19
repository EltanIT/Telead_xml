package com.example.telead_xml.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentSearchResultListBinding
import com.example.telead_xml.domen.objects.FilterData
import com.example.telead_xml.view.adapter.FragmentAdapter
import com.example.telead_xml.view.listener.FilterChangedListener

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
        vm.setBundle(arguments)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setting() {
        binding.mentors.isEnabled = false
        binding.courses.isEnabled = false
        binding.courses.isSelected = true

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

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

        binding.search.addTextChangedListener {
            vm.redactRequest(it.toString())
        }

        binding.search.setOnTouchListener { view, motionEvent ->
            val RIGHT = 2
            if (motionEvent.action == MotionEvent.ACTION_UP){
                if (motionEvent.rawX >= (binding.search.right - binding.search.compoundDrawables[RIGHT].bounds.width())){
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.full_home_container_view, FilterFragment(object: FilterChangedListener{
                            override fun changes() {
                                vm.filterChanged()
                            }

                        }))
                        .addToBackStack("filter")
                        .commit()
                }
            }
            false
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
        vm.requestData.observe(viewLifecycleOwner){
            if (it!=null){
                binding.search.hint = it
                val adapter = FragmentAdapter(FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, requireActivity().supportFragmentManager)
                adapter.addFrag(CoursesListFragment(it?:""))
                adapter.addFrag(MentorsListFragment(it?:""))
                binding.viewpager.adapter = adapter

                binding.mentors.isEnabled = true
                binding.courses.isEnabled = true
            }
        }
    }
}


class SearchResultListViewModel(val context: Context): ViewModel(){
    val requestData = MutableLiveData<String?>()
    private var newRequest = ""

    fun setBundle(arguments: Bundle?) {
        if (arguments!=null){
            requestData.value = arguments.getString("request", null)
        }else{
            requestData.value = null
        }
    }

    fun redactRequest(toString: String) {
        newRequest = toString
    }

    fun search(){
        requestData.value = newRequest
    }

    fun filterChanged() {
        requestData.value = requestData.value
    }
}

class SearchResultListViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchResultListViewModel(context = context) as T
    }
}