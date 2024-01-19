package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCourseByIdRepository
import com.example.telead_xml.data.repository.course.GetCoursesForFiltersRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.databinding.FragmentReviewsBinding
import com.example.telead_xml.domen.objects.CategoryData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.ReviewData
import com.example.telead_xml.view.adapter.PopularCoursesCategoriesHomeAdapter
import com.example.telead_xml.view.adapter.ReviewsAdapter
import com.example.telead_xml.view.listener.CategoryListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewsFragment : Fragment() {

    private lateinit var binding: FragmentReviewsBinding
    private lateinit var vm: ReviewsViewModel

    private var reviewAdapter: ReviewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, ReviewsViewModelFactory(requireContext()))[ReviewsViewModel::class.java]
        setting()
        subscriptions()
        vm.setting(arguments)
        return binding.root
    }

    private fun setting() {
        binding.reviewsBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", vm.getId())
            val fragment = WriteAReviewsFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, fragment)
                .addToBackStack("writeReview")
                .commit()
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun subscriptions() {
        vm.categoryList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.reviewsCategoryRv.adapter = PopularCoursesCategoriesHomeAdapter(it, object : CategoryListener{
                    override fun click(name: String?) {
                        reviewAdapter?.filter(name)
                    }

                })
            }
        }

        vm.reviewsList.observe(viewLifecycleOwner){
            if (it!=null){
                reviewAdapter = ReviewsAdapter(it)
                binding.reviewsRv.adapter

            }
        }

        vm.stateLoading.observe(viewLifecycleOwner){
            if (it == true){

            }else{
                binding.raging.text = "4.8"
                binding.reviewsCount.text = "448"
            }
        }
    }
}


class ReviewsViewModel(val context: Context): ViewModel(){
    val categoryList = MutableLiveData<ArrayList<CategoryData>>(ArrayList())
    val reviewsList = MutableLiveData<ArrayList<ReviewData>>(ArrayList())
    val stateLoading = MutableLiveData<Boolean?>()
    private var id = ""

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun setting(bundle: Bundle?){
        if (bundle!=null){
            id = bundle.getString("id", "")
            getCategoryList()
            getReviews(id)
        }
    }

    private fun getReviews(id: String?) {
        coroutineScope.launch {
            val responseData = GetCourseByIdRepository().request(id?:"")
            if(responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<ArrayList<CoursesData>>(){}.type
                Log.i("swagger", responseData.body.toString())
                try{
                    reviewsList.value?.addAll(gson.fromJson(responseData.body?:"",type))
                    reviewsList.postValue(reviewsList.value)
                }catch (e: Exception){
                    reviewsList.value?.addAll(ArrayList())
                }
            }else{
                reviewsList.value?.add(ReviewData(name = "Наташа Гончарова", rating = 4.6f, date =  "2 Недели Назад", likes = 532,
                    description = "Курс очень хорош, долор ветерм, как всегда, как всегда, привет капитану.."))
                reviewsList.value?.add(ReviewData(name = "Никита Мельников", rating = 4.3f, date =  "1 Неделю Назад", likes = 322,
                    description = "Курс очень хорош, долор ветерм, как всегда, как всегда, привет капитану.."))
                reviewsList.value?.add(ReviewData(name = "Валерий Пакуситский", rating = 4.8f, date =  "2 Недели Назад", likes = 761,
                    description = "Курс очень хорош, долор ветерм, как всегда, как всегда, привет капитану.."))
                reviewsList.value?.add(ReviewData(name = "Наташа Гончарова", rating = 4.6f, date =  "2 Недели Назад", likes = 532,
                    description = "Курс очень хорош, долор ветерм, как всегда, как всегда, привет капитану.."))
                reviewsList.value?.add(ReviewData(name = "Наташа Гончарова", rating = 4.6f, date =  "2 Недели Назад", likes = 532,
                    description = "Курс очень хорош, долор ветерм, как всегда, как всегда, привет капитану.."))
                reviewsList.postValue(reviewsList.value)
            }
            stateLoading.postValue(responseData?.response?.isSuccessful)
        }
    }

    private fun getCategoryList() {

    }

    fun getId(): String {
        return id
    }

}

class ReviewsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReviewsViewModel(context = context) as T
    }
}