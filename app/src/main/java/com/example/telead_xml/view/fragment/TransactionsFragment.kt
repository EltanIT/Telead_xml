package com.example.telead_xml.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telead_xml.databinding.FragmentTransactionsBinding
import com.example.telead_xml.domen.objects.TransactionsData
import com.example.telead_xml.view.adapter.TransactionsAdapter

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var vm: TransactionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, TransactionsViewModelFactory(requireContext()))[TransactionsViewModel::class.java]
        subscription()
        setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.transactionsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.transactionRv.adapter = TransactionsAdapter(it)
            }
        }
    }

}


class TransactionsViewModel(val context: Context): ViewModel(){
    val transactionsList = MutableLiveData(ArrayList<TransactionsData>())

    private fun getList(){
        transactionsList.value?.add(TransactionsData("Николай", "Web Designer",""))
        transactionsList.value?.add(TransactionsData("Николай", "Web Designer",""))
        transactionsList.value?.add(TransactionsData("Николай", "Web Designer",""))
        transactionsList.value?.add(TransactionsData("Николай", "Web Designer",""))
        transactionsList.value?.add(TransactionsData("Николай", "Web Designer",""))

        transactionsList.value = transactionsList.value
    }


    fun setting() {
        getList()
    }
}

class TransactionsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionsViewModel(context = context) as T
    }
}