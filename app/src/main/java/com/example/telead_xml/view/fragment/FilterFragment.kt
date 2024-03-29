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
import com.example.telead_xml.data.repository.course_category.GetCoursesCategoriesRepository
import com.example.telead_xml.data.shared_pref.GetAccessToken
import com.example.telead_xml.data.shared_pref.GetFilter
import com.example.telead_xml.data.shared_pref.SaveFilter
import com.example.telead_xml.databinding.FragmentFilterBinding
import com.example.telead_xml.domen.objects.FilterCategoryData
import com.example.telead_xml.domen.objects.FilterData
import com.example.telead_xml.domen.objects.FilterRatingData
import com.example.telead_xml.view.adapter.filters.FilterCategoryAdapter
import com.example.telead_xml.view.adapter.filters.FilterFeaturesAdapter
import com.example.telead_xml.view.adapter.filters.FilterPriceAdapter
import com.example.telead_xml.view.adapter.filters.FilterRatingAdapter
import com.example.telead_xml.view.adapter.filters.FilterVideoDurationAdapter
import com.example.telead_xml.view.adapter.filters.FilterlevelsAdapter
import com.example.telead_xml.view.listener.FilterChangedListener
import com.example.telead_xml.view.listener.FilterListener
import com.example.telead_xml.view.listener.FilterRatingListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterFragment(val listener: FilterChangedListener) : Fragment() {

    private lateinit var binding: FragmentFilterBinding
    private lateinit var vm: FilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, FilterViewModelFactory(requireContext()))[FilterViewModel::class.java]
        subscription()
        setting()
        vm.setting(listener)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setting() {
        binding.apply.setOnClickListener {
            vm.postData()
            vm.listenning()
        }
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.clear.setOnClickListener {
            vm.clearList()
        }
    }

    private fun subscription() {
        vm.categoryList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.subCategoriesRv.adapter = FilterCategoryAdapter(it, vm.filter.value?.categoryIds, object: FilterListener{
                    override fun add(name: String?) {
                        vm.redactCategory(name, true)
                    }

                    override fun remove(name: String?) {
                        vm.redactCategory(name, false)
                    }

                })
            }
        }
        vm.levelsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.levelsRv.adapter = FilterlevelsAdapter(it, vm.filter.value?.difficultLevels, object: FilterListener{
                    override fun add(name: String?) {
                        vm.redactLevel(name, true)
                    }

                    override fun remove(name: String?) {
                        vm.redactLevel(name, false)
                    }

                })
            }
        }
        vm.priceList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.priceRv.adapter = FilterPriceAdapter(it, vm.filter.value?.costTypes, object: FilterListener{
                    override fun add(name: String?) {
                        vm.redactCostType(name, true)
                    }

                    override fun remove(name: String?) {
                        vm.redactCostType(name, false)
                    }

                })
            }
        }
        vm.featuresList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.featuresRv.adapter = FilterFeaturesAdapter(it, vm.filter.value?.categoryIds, object: FilterListener{
                    override fun add(name: String?) {

                    }

                    override fun remove(name: String?) {

                    }

                })
            }
        }
        vm.ratingList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.ratingRv.adapter = FilterRatingAdapter(it, vm.filter.value?.minimumRating, object: FilterRatingListener{
                    override fun addRating(rating: Double) {
                        vm.redactRating(rating, true)
                    }

                    override fun removeRating(rating: Double) {
                        vm.redactRating(rating, false)
                    }

                })
            }
        }
        vm.videoDurationList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.videoDurationRv.adapter = FilterVideoDurationAdapter(it, vm.filter.value?.categoryIds, object: FilterListener{
                    override fun add(name: String?) {

                    }

                    override fun remove(name: String?) {

                    }

                })
            }
        }
        vm.filter.observe(viewLifecycleOwner){
            if (it!=null){
               
            }
        }

        vm.statePost.observe(viewLifecycleOwner){
            if (it!=null){
                if (it){
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

}


class FilterViewModel(val context: Context): ViewModel(){
    val filter = MutableLiveData(FilterData())
    val statePost = MutableLiveData<Boolean>()
    private lateinit var listener: FilterChangedListener

    val categoryList = MutableLiveData<ArrayList<FilterCategoryData>?>()
    val levelsList = MutableLiveData(ArrayList<FilterCategoryData>())
    val priceList = MutableLiveData(ArrayList<FilterCategoryData>())
    val featuresList = MutableLiveData(ArrayList<FilterCategoryData>())
    val ratingList = MutableLiveData(ArrayList<FilterRatingData>())
    val videoDurationList = MutableLiveData(ArrayList<FilterCategoryData>())

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val getCoursesCategoriesRepository = GetCoursesCategoriesRepository()

    fun setting(list: FilterChangedListener) {
        listener = list
        getFilter()
        coroutineScope.launch {
            getCategory()
        }
        coroutineScope.launch {
            getLevels()
        }
        coroutineScope.launch {
            getPrice()
            getFeatures()
            getRating()
            getDuration()
            redactFormatType("",true)
            redactCategory("All", true)
        }
    }

    private fun getFilter() {
        val gson = Gson()
        val string = GetFilter().execute(context)?:""
        filter.value = gson.fromJson(string, FilterData::class.java)?: FilterData()
    }

    fun redactCategory(id: String?, state: Boolean){
        if (state){
            if (id != null) {
                filter.value?.categoryIds?.clear()
                filter.value?.categoryIds?.add(id)
            }
        }else{
            filter.value?.categoryIds?.remove(id)
        }
    }
    fun redactCostType(name: String?, state: Boolean){
        if (state){
            if (name != null) {
                filter.value?.costTypes?.add(name)
            }
        }else{
            filter.value?.costTypes?.remove(name)
        }
    }
    fun redactFormatType(name: String, state: Boolean){
        if (state){
            filter.value?.formatTypes?.clear()
            filter.value?.formatTypes?.add("Online")
        }else{
            filter.value?.formatTypes?.remove(name)
        }
    }
    fun redactLevel(name: String?, state: Boolean){
        if (state){
            if (name != null) {
                filter.value?.difficultLevels?.add(name)
            }
        }else{
            filter.value?.difficultLevels?.remove(name)
        }
    }
    fun redactRating(rating: Double, state: Boolean){
        if (state){
            filter.value?.minimumRating = rating
        }else{
            filter.value?.minimumRating = 0.0
        }

    }

    private fun getCategory(){
        val responseData = getCoursesCategoriesRepository.request(GetAccessToken().execute(context)?:"")
        if (responseData?.response?.isSuccessful == true){
            val gson = Gson()
            val itemType = object : TypeToken<ArrayList<FilterCategoryData>>() {}.type
            categoryList.postValue(gson.fromJson<ArrayList<FilterCategoryData>>(responseData.body, itemType))
        }
    }
    private fun getLevels(){
        levelsList.value?.add(FilterCategoryData(name ="All Levels"))
        levelsList.value?.add(FilterCategoryData(name ="Beginners"))
        levelsList.value?.add(FilterCategoryData(name ="Intermediate"))
        levelsList.value?.add(FilterCategoryData(name ="Expert"))
        levelsList.postValue(levelsList.value)
    }
    private fun getPrice(){
        priceList.value?.add(FilterCategoryData(name ="Paid"))
        priceList.value?.add(FilterCategoryData(name ="Free"))
        priceList.postValue(priceList.value)
    }
    private fun getFeatures(){
        featuresList.value?.add(FilterCategoryData(name ="All Captian"))
        featuresList.value?.add(FilterCategoryData(name ="Quizzes"))
        featuresList.value?.add(FilterCategoryData(name ="Coding Exercise"))
        featuresList.value?.add(FilterCategoryData(name ="Practice Tests"))
        featuresList.postValue(featuresList.value)
    }
    private fun getRating(){
        ratingList.value?.add(FilterRatingData("4.5 & Up Above", 4.5))
        ratingList.value?.add(FilterRatingData("4.0 & Up Above", 4.0))
        ratingList.value?.add(FilterRatingData("3.5 & Up Above", 3.5))
        ratingList.value?.add(FilterRatingData("3.0 & Up Above", 3.0))
        ratingList.postValue(ratingList.value)
    }
    private fun getDuration(){
        videoDurationList.value?.add(FilterCategoryData(name ="0-2 Hours"))
        videoDurationList.value?.add(FilterCategoryData(name ="3-6 Hours"))
        videoDurationList.value?.add(FilterCategoryData(name ="7-16 Hours"))
        videoDurationList.value?.add(FilterCategoryData(name ="17+ Hours"))
        videoDurationList.postValue(videoDurationList.value)
    }

    fun postData() {
        val gson = Gson()
        SaveFilter().execute(gson.toJson(filter.value), context)
        Log.i("swagger", gson.toJson(filter.value))
        statePost.value = true
    }

    fun clearList() {
        filter.value = FilterData()
        postData()
        listener.changes()
    }

    fun listenning() {
        listener.changes()
    }

}

class FilterViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilterViewModel(context = context) as T
    }
}