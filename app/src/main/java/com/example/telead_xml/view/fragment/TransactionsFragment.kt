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
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentTransactionsBinding
import com.example.telead_xml.domen.objects.TransactionsData
import com.example.telead_xml.view.adapter.TransactionsAdapter
import com.example.telead_xml.view.listener.TransactionListener

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
        vm.setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.transactionsList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.transactionRv.adapter = TransactionsAdapter(it, object: TransactionListener{
                    override fun click(id: String) {
                        val bundle = Bundle()
                        bundle.putString("id", id)
                        val fragment = EReceiptFragment()
                        fragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.full_home_container_view, fragment)
                            .addToBackStack("callsVoice")
                            .commit()
                    }

                })
            }
        }
    }

}


class TransactionsViewModel(val context: Context): ViewModel(){
    val transactionsList = MutableLiveData(ArrayList<TransactionsData>())

    private fun getList(){
        transactionsList.value?.add(TransactionsData("Создание личного брендинга", "Веб Дизайн",""))
        transactionsList.value?.add(TransactionsData("Освоение Blender 3D", "Ui/UX Дизайн",""))
        transactionsList.value?.add(TransactionsData("Веб-разработка с полным стеком", "Веб Разработка",""))

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