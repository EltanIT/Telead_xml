package com.example.telead_xml.view.fragment

import android.content.Context
import android.content.pm.ActivityInfo
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
import com.example.telead_xml.data.repository.course.GetCourseByIdRepository
import com.example.telead_xml.databinding.FragmentSingleCourseDetailsBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.FragmentAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleCourseDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSingleCourseDetailsBinding
    private lateinit var vm: SingleCourseDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentSingleCourseDetailsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, SingleCourseDetailsViewModelFactory(requireContext()))[SingleCourseDetailsViewModel::class.java]
        subscription()
        setting()
        vm.getBundle(arguments)
        return binding.root
    }

    private fun setting() {
        binding.about.isSelected = true

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

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
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
                binding.enroll.text = "Записаться на курс - ${it.price.toString()}₽"
                binding.rating.text = it.rating.toString()
                Picasso.with(requireContext())
                    .load(it.imageUrl?:"")
                    .into(binding.image)

                val bundle = Bundle()
                bundle.putString("id", it.id)
                bundle.putString("desctiption", it.description)
                val cf = CurriculumCourseFragment()
                val af = AboutCourseFragment()
                cf.arguments = bundle
                val adapter =  FragmentAdapter(androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, childFragmentManager)
                adapter.addFrag(af)
                adapter.addFrag(cf)
                binding.viewPager.adapter = adapter
            }else{
                //
                binding.name.text = "Дизайн сайтов"
                binding.category.text ="Дизайн"
                binding.timeText.text = "5 Часов"
                binding.group.text = "21"
                binding.price.text = "28"
                binding.enroll.text = "Записаться на курс - 28₽"
                binding.rating.text = "4.2"
                Picasso.with(requireContext())
                    .load("https://gk-c.ru/wp-content/uploads/2022/01/graficheskiy-dizayner.jpg")
                    .into(binding.image)

                val bundle = Bundle()
                bundle.putString("id", it?.id)
                bundle.putString("desctiption", it?.description)
                val cf = CurriculumCourseFragment()
                val af = AboutCourseFragment()
                cf.arguments = bundle
                val adapter =  FragmentAdapter(androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, childFragmentManager)
                adapter.addFrag(af)
                adapter.addFrag(cf)
                binding.viewPager.adapter = adapter
                //
            }
        }

        vm.stateGet.observe(viewLifecycleOwner){
            if (it == true){

            }
        }
    }
}


class SingleCourseDetailsViewModel(val context: Context): ViewModel(){
    val course = MutableLiveData<CoursesData?>(null)
    val stateGet = MutableLiveData<Boolean?>(null)

    private val getCourseByIdRepository = GetCourseByIdRepository()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    fun getBundle(bundle: Bundle?){
        if (bundle!=null){
            val id = bundle.getString("id", null)
            if (id!=null && id.isNotEmpty()){
                getCourse(id)
            }
            else{
                course.value = null
            }
        }
    }

    fun getCourse(id: String){
        coroutineScope.launch {
            val responseData = getCourseByIdRepository.request(id)
            if (responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<CoursesData>(){}.type
                course.postValue(gson.fromJson(responseData.body, type))
            }
            stateGet.postValue(responseData?.response?.isSuccessful)
        }

    }

    fun getId(): String {
        return course.value?.id ?: ""
    }
}

class SingleCourseDetailsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleCourseDetailsViewModel(context = context) as T
    }
}