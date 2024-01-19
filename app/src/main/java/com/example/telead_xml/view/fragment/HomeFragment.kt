package com.example.telead_xml.view.fragment

import PostBookmarkRepository
import RemoveBookmarkRepository
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCoursesForFiltersRepository
import com.example.telead_xml.data.repository.course_category.GetCoursesCategoriesRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.data.shared_pref.GetFilter
import com.example.telead_xml.databinding.FragmentHomeBinding
import com.example.telead_xml.domen.objects.CategoryData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.FilterData
import com.example.telead_xml.domen.objects.MentorData
import com.example.telead_xml.view.adapter.CategoriesHomeAdapter
import com.example.telead_xml.view.adapter.PopularCoursesCategoriesHomeAdapter
import com.example.telead_xml.view.adapter.PopularCoursesHomeAdapter
import com.example.telead_xml.view.adapter.RecommendationsHomeAdapter
import com.example.telead_xml.view.adapter.TopMentorHomeAdapter
import com.example.telead_xml.view.listener.CategoryListener
import com.example.telead_xml.view.listener.CourseListener
import com.example.telead_xml.view.listener.FilterChangedListener
import com.example.telead_xml.view.listener.MentorListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var vm: HomeViewModel

    private var courseAdapter: PopularCoursesHomeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, HomeViewModelFactory(requireContext()))[HomeViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    override fun onResume() {
        vm.setting()
        super.onResume()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setting() {
        binding.categoryBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, CategoryFragment())
                .addToBackStack("category")
                .commit()
        }
        binding.popularCoursesBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.home_container_view, PopularCoursesFragment())
                .addToBackStack("courses")
                .commit()
        }
        binding.topMentorsBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, TopMentorsFragment())
                .addToBackStack("mentors")
                .commit()
        }

        binding.search.setOnTouchListener{v, event ->
            val DRAWABLE_RIGHT = 2;
            if(event.action == MotionEvent.ACTION_UP) {
                if(event.rawX >= (binding.search.right - binding.search.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.full_home_container_view, FilterFragment(object: FilterChangedListener{
                            override fun changes() {
                                vm.filterChanged()
                            }

                        }))
                        .addToBackStack("filter")
                        .commit()
                    true
                }else{
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.full_home_container_view, SearchFragment())
                        .addToBackStack("search")
                        .commit()
                    true
                }
            }

            false
        }

        binding.notification.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, NotificationFragment())
                .addToBackStack("notification")
                .commit()
        }
    }

    private fun subscription() {

        vm.recommendationsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.recommendationsRv.adapter = RecommendationsHomeAdapter(it)
            }
        }
        vm.categoryList.observe(viewLifecycleOwner){
            if (it != null){
                if (it.size>0){
                    binding.categoryRv.adapter = CategoriesHomeAdapter(it)
                }
            }
        }
        vm.popularCoursesCategoryList.observe(viewLifecycleOwner){
            if (it != null){
                if (it.size>0){
                    binding.coursesCategoryRv.adapter = PopularCoursesCategoriesHomeAdapter(it, object: CategoryListener{
                        override fun click(name: String?) {

                        }

                    })
                }
            }
        }
        vm.popularCoursesList.observe(viewLifecycleOwner){
            if (it != null){
                if (it.size>0){
                    courseAdapter = PopularCoursesHomeAdapter(it, object: CourseListener{
                        override fun click(id: String?) {
                            val bundle = Bundle()
                            bundle.putString("id", id?:"")
                            val fragment = SingleCourseDetailsFragment()
                            fragment.arguments = bundle

                            requireActivity().supportFragmentManager.beginTransaction()
                                .add(R.id.full_home_container_view, fragment)
                                .addToBackStack("singleCourseDetails")
                                .commit()
                        }

                        override fun addBookmark(id: String?) {
                            vm.addBookmark(id)
                        }

                        override fun removeBookmark(id: String?) {
                            vm.removeBookmark(id)
                        }

                    })
                    binding.coursesRv.adapter = courseAdapter
                }

            }
        }
        vm.topMentorList.observe(viewLifecycleOwner){
            if (it != null){
                if (it.size>0){
                    binding.mentorRv.adapter = TopMentorHomeAdapter(it, object: MentorListener{
                        override fun click(id: String) {
                            val bundle = Bundle()
                            bundle.putString("id", id)
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

        vm.statePost.observe(viewLifecycleOwner){
            when(it){
                200->{
                    Toast.makeText(requireContext(), "Добавлено в закладки", Toast.LENGTH_SHORT).show()
                }
                409->{
                    Toast.makeText(requireContext(), "Конфликт", Toast.LENGTH_SHORT).show()
                }
                else->{
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}


class HomeViewModel(val context: Context): ViewModel(){
    val categoryList = MutableLiveData(ArrayList<CategoryData>())
    val recommendationsList = MutableLiveData(ArrayList<CoursesData>())
    val popularCoursesCategoryList = MutableLiveData(ArrayList<CategoryData>())
    val popularCoursesList = MutableLiveData(ArrayList<CoursesData>())
    val topMentorList = MutableLiveData(ArrayList<MentorData>())
    private var filter = FilterData()

    val statePost = MutableLiveData<Int?>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val postBookmarkRepository = PostBookmarkRepository()
    private val removeBookmarkRepository = RemoveBookmarkRepository()
    private val getCoursesCategoriesRepository = GetCoursesCategoriesRepository()

    private fun getRecommendations(){
        coroutineScope.launch {
            filter.countSkipped = popularCoursesList.value?.size
            val responseData = GetCoursesForFiltersRepository().request(filter, GetAccessToken().execute(context)?:"")
            if(responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ArrayList<CoursesData>>(){}.type
                Log.i("swagger", responseData.body.toString())
                try{
                    recommendationsList.value?.addAll(gson.fromJson(responseData.body?:"",type))
                    recommendationsList.postValue(popularCoursesList.value)
                }catch (e: Exception){
                    recommendationsList.value?.addAll(ArrayList())
                }
            }else{
                recommendationsList.value?.add(CoursesData(name = "Специальное предложение"))
                recommendationsList.value?.add(CoursesData(name = "Специальное предложение"))
                recommendationsList.value?.add(CoursesData(name = "Специальное предложение"))
                recommendationsList.postValue(popularCoursesList.value)
            }
        }

    }
    private fun getCategory(){
        coroutineScope.launch {
            val responseData = getCoursesCategoriesRepository.request(GetAccessToken().execute(context)?:"")
            if(responseData?.response?.isSuccessful == true){
                    val gson = Gson()
                    val type = object: TypeToken<ArrayList<CategoryData>>(){}.type
                    categoryList.postValue(gson.fromJson(responseData.body?:"",type))
                    Log.i("swagger", responseData.body.toString())
            }else{
                categoryList.value?.add(CategoryData(name = "3Д Дизайн"))
                categoryList.value?.add(CategoryData(name = "Графический Дизайн"))
                categoryList.value?.add(CategoryData(name = "Мобильная разработка"))
                categoryList.postValue(categoryList.value)
            }
        }
    }
    private fun getPopularCoursesCategory(){
        coroutineScope.launch {
            val responseData = getCoursesCategoriesRepository.request(GetAccessToken().execute(context)?:"")
            if (responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ArrayList<CategoryData>>(){}.type
                popularCoursesCategoryList.value?.add(CategoryData(name = "Все"))
                popularCoursesCategoryList.value?.addAll(gson.fromJson(responseData.body?:"",type) as ArrayList<CategoryData>)
                popularCoursesCategoryList.postValue(popularCoursesCategoryList.value)
                Log.i("swagger", responseData.body.toString())
            }else{
                popularCoursesCategoryList.value?.add(CategoryData(name = "Все"))
                popularCoursesCategoryList.value?.add(CategoryData(name = "3Д Дизайн"))
                popularCoursesCategoryList.value?.add(CategoryData(name = "Искусство и гуманитарные науки"))
                popularCoursesCategoryList.value?.add(CategoryData(name = "Графический Дизайн"))
                popularCoursesCategoryList.postValue(popularCoursesCategoryList.value)
            }
        }

    }

    private fun getPopularCourses(){
        coroutineScope.launch {
            filter.countSkipped = popularCoursesList.value?.size
            val responseData = GetCoursesForFiltersRepository().request(filter, GetAccessToken().execute(context)?:"")
            if(responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ArrayList<CoursesData>>(){}.type
                Log.i("swagger", responseData.body.toString())
                try{
                    popularCoursesList.value?.addAll(gson.fromJson(responseData.body?:"",type))
                    popularCoursesList.postValue(popularCoursesList.value)
                }catch (e: Exception){
                    popularCoursesList.value?.addAll(ArrayList())
                }
            }else{
                popularCoursesList.value?.add(CoursesData())
                popularCoursesList.value?.add(CoursesData())
                popularCoursesList.value?.add(CoursesData())
                popularCoursesList.postValue(popularCoursesList.value)
            }
        }

    }

    private fun getTopMentor(){
        topMentorList.value = ArrayList()
        topMentorList.value?.add(MentorData("Вадим", "Николаенко", "S", "3D Дизайн", "https://vpautinu.com/wp-content/uploads/2019/11/5.Rabota-za-noutbukom-2.jpeg"))
        topMentorList.value?.add(MentorData("Игорь", "Петренко", "S", "Фотошоп", "https://vpautinu.com/wp-content/uploads/2019/11/5.Rabota-za-noutbukom-2.jpeg"))
        topMentorList.value?.add(MentorData("Николай", "Хромов", "S", "Графический дизайн", "https://vpautinu.com/wp-content/uploads/2019/11/5.Rabota-za-noutbukom-2.jpeg"))
        topMentorList.value?.add(MentorData("Виктория", "Тратунов", "S", "Arts & Humanities", "https://vpautinu.com/wp-content/uploads/2019/11/5.Rabota-za-noutbukom-2.jpeg"))
        topMentorList.value = topMentorList.value
    }

    fun setting() {
        getFilter()
        getCategory()
        getPopularCoursesCategory()
        getPopularCourses()
        getTopMentor()
        getRecommendations()
    }

    private fun getFilter() {
        val gson = Gson()
        val gsonFilter = GetFilter().execute(context)?:""
        filter = gson.fromJson(gsonFilter, FilterData::class.java)
        Log.i("swagger", gsonFilter)
    }

    fun addBookmark(id: String?) {
        coroutineScope.launch {
            val responseData = postBookmarkRepository.request(id?:"", GetAccessToken().execute(context)?:"")
            statePost.postValue(responseData?.response?.code)
        }
    }

    fun removeBookmark(id: String?) {
        coroutineScope.launch {
            val responseData = removeBookmarkRepository.request(id?:"", GetAccessToken().execute(context)?:"")
            statePost.postValue(responseData?.response?.code)
        }
    }

    fun filterChanged() {
        getFilter()
    }
}

class HomeViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(context = context) as T
    }
}