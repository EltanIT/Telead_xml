package com.example.telead_xml.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentSingleCourseDetailsBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.IntroAdapter
import com.google.gson.Gson

class SingleCourseDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSingleCourseDetailsBinding
    private lateinit var vm: SingleCourseDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleCourseDetailsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, SingleCourseDetailsViewModelFactory(requireContext()))[SingleCourseDetailsViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {
        binding.about.isSelected = true
        val bundle = arguments
        vm.getBundle(bundle)

        val adapter =  IntroAdapter(androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, requireActivity().supportFragmentManager)
        adapter.addFrag(AboutCourseFragment(vm.course.value?.description))
        adapter.addFrag(CurriculumCourseFragment())
        binding.viewPager.adapter = adapter

        binding.curriculcum.setOnClickListener {
            binding.viewPager.setCurrentItem(1, true)
            binding.about.isSelected = false
            binding.curriculcum.isSelected = true
        }
        binding.about.setOnClickListener {
            binding.viewPager.setCurrentItem(0, true)
            binding.about.isSelected = true
            binding.curriculcum.isSelected = false
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
                    binding.about.isSelected = true
                    binding.curriculcum.isSelected = false
                }else{
                    binding.about.isSelected = false
                    binding.curriculcum.isSelected = true
                }
            }
            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        binding.enroll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", vm.getId())
            val fragment = CurriculcumFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, fragment)
                .addToBackStack("curriculcum")
                .commit()
        }
    }


    private fun subscription() {
        vm.course.observe(viewLifecycleOwner){
            if (it!=null){
                binding.name.text = it.name
                binding.category.text = it.benefits[0].name
                binding.timeText.text = (it.durationInMinutes!!/60).toString() + " Часов"
                binding.group.text = it.countStudents.toString()
                binding.price.text = it.price.toString()
                binding.image.setImageURI(Uri.parse(it.imageUrl))
            }
        }
    }
}


class SingleCourseDetailsViewModel(val context: Context): ViewModel(){
    val course = MutableLiveData<CoursesData?>()


    fun getBundle(bundle: Bundle?){
        if (bundle!=null){
            val courseJson = bundle.getString("course", null)
            if (courseJson!=null){
                val gson = Gson()
                course.value = gson.fromJson(courseJson, CoursesData::class.java)
            }
        }
    }

    fun getId(): String? {
        return course.value?.id
    }
}

class SingleCourseDetailsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleCourseDetailsViewModel(context = context) as T
    }
}