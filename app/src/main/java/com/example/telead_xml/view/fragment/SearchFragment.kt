package com.example.telead_xml.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentSearchBinding
import com.example.telead_xml.domen.objects.SearchHistoryData
import com.example.telead_xml.view.adapter.PopularFullCoursesAdapter
import com.example.telead_xml.view.adapter.SearchHistorylAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var vm: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, SearchViewModelFactory(requireContext()))[SearchViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    override fun onResume() {
        vm.setting()
        binding.search.isFocused
        super.onResume()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setting() {
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.search.setOnTouchListener {v, event ->
            val RIGHT = 2
            if(event.action == MotionEvent.ACTION_UP) {
                if(event.rawX >= (binding.search.right - binding.search.compoundDrawables[RIGHT].bounds.width())) {
                    val bundle = Bundle()
                    bundle.putString("request", vm.getRequest()?:"")
                    val fragment = SearchResultListFragment()
                    fragment.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.home_container_view, fragment)
                        .addToBackStack("searchResultList")
                        .commit()
                    true
                }
            }
             false
        }
        binding.search.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.home_container_view, SearchResultListFragment())
                .addToBackStack("searchResultList")
                .commit()
        }
        binding.search.addTextChangedListener {
            vm.redactSearch(it.toString())
        }
        binding.search.setOnEditorActionListener(object: OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH){
                    requireActivity().supportFragmentManager.popBackStack()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.home_container_view, SearchResultListFragment())
                        .addToBackStack("searchResultList")
                        .commit()

                    return true
                }
                return false
            }

        })
    }

    private fun subscription() {
        vm.historyList.observe(viewLifecycleOwner){
            if (it != null){
                binding.searchHistoryRv.adapter = SearchHistorylAdapter(it)
            }
        }
    }
}


class SearchViewModel(val context: Context): ViewModel(){
    val historyList = MutableLiveData(ArrayList<SearchHistoryData>())
    val searchRequest = MutableLiveData<String>()

    private fun getHistory(){
       historyList.value?.add(SearchHistoryData("3D design"))
       historyList.value?.add(SearchHistoryData("3D design"))
       historyList.value?.add(SearchHistoryData("3D design"))
       historyList.value?.add(SearchHistoryData("3D design"))
       historyList.value?.add(SearchHistoryData("3D design"))

        historyList.value = historyList.value
    }

    fun redactSearch(txt: String){
        searchRequest.value = txt
    }


    fun setting() {
        getHistory()
    }

    fun getRequest(): String? {
        return searchRequest.value
    }
}

class SearchViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(context = context) as T
    }
}