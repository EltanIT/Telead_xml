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
import com.example.telead_xml.databinding.FragmentSingleMentorsDetailsBinding
import com.example.telead_xml.domen.objects.SingleMentorsData
import com.google.gson.Gson

class SingleMentorsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSingleMentorsDetailsBinding
    private lateinit var vm: SingleMentorsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleMentorsDetailsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, SingleMentorsDetailsViewModelFactory(requireContext()))[SingleMentorsDetailsViewModel::class.java]
        subscription()
        setting()
        vm.getBundle(arguments)
        return binding.root
    }


    private fun setting() {
        binding.couses.isSelected = true

        binding.ratings.setOnClickListener {
            binding.viewPager.setCurrentItem(1, true)
            binding.couses.isSelected = false
            binding.ratings.isSelected = true
        }
        binding.couses.setOnClickListener {
            binding.viewPager.setCurrentItem(0, true)
            binding.couses.isSelected = true
            binding.ratings.isSelected = false
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
                    binding.couses.isSelected = true
                    binding.ratings.isSelected = false
                }else{
                    binding.couses.isSelected = false
                    binding.ratings.isSelected = true
                }
            }
            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


    private fun subscription() {
        vm.mentorDetails.observe(viewLifecycleOwner){
            if (it!=null){
                binding.name.text = it.name
                binding.post.text = it.post
                binding.coursesCount.text = it.courses.toString()
                binding.students.text = it.students.toString()
                binding.ratingCount.text = it.ratings.toString()

                val adapter = com.example.telead_xml.view.adapter.FragmentAdapter(FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, childFragmentManager)
                adapter.addFrag(CoursesListFragment(vm.getRequest()?:""))
                adapter.addFrag(SingleMentorsDetailsRatingsFragment())
                binding.viewPager.adapter = adapter
            }
        }
    }

}


class SingleMentorsDetailsViewModel(val context: Context): ViewModel(){
    val mentorDetails = MutableLiveData(
        SingleMentorsData("Christopher J. Levine",
        "Graphic Designer At Google",
        26,
        25032,
        8532,
        "")
    )


    fun getBundle(bundle: Bundle?){
        if (bundle!=null){
            val courseJson = bundle.getString("mentor", null)
            if (courseJson!=null){
                val gson = Gson()
                mentorDetails.value = gson.fromJson(courseJson, SingleMentorsData::class.java)
            }
        }
    }

    fun getRequest(): String? {
        return mentorDetails.value?.post
    }
}

class SingleMentorsDetailsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleMentorsDetailsViewModel(context = context) as T
    }
}