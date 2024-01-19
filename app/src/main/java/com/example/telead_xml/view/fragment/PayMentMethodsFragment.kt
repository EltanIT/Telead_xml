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
import com.example.telead_xml.R
import com.example.telead_xml.data.repository.course.GetCourseByIdRepository
import com.example.telead_xml.databinding.FragmentPaymentMethodsBinding
import com.example.telead_xml.domen.objects.CardData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.adapter.PaymentMethodsAdapter
import com.example.telead_xml.view.listener.PayMethodsListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PayMentMethodsFragment : Fragment() {

    private lateinit var binding: FragmentPaymentMethodsBinding
    private lateinit var vm: PayMentMethodsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentMethodsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, PayMentMethodsViewModelFactory(requireContext()))[PayMentMethodsViewModel::class.java]
        setting()
        subscriptions()
        vm.postData(arguments)
        return binding.root
    }

    private fun subscriptions() {
        vm.PaymentMethods.observe(viewLifecycleOwner){
            if (it!=null){
                binding.paymentMethodsRv.adapter = PaymentMethodsAdapter(it, object: PayMethodsListener{
                    override fun check(cardData: CardData) {
                        vm.redactData(cardData)
                    }

                })
            }
        }

        vm.course.observe(viewLifecycleOwner){
            if (it!=null){
                binding.name.text = it.name
                if (it.benefits.size > 0){
                    binding.category.text = it.benefits[0].name
                }
                binding.image.setImageURI(Uri.parse(it.imageUrl))
            }else{
                binding.name.text = "Сделай свой графический дизайн"
                binding.category.text = "Графический дизайн"
                binding.image.setImageURI(Uri.parse("https://kopiberi.ru/uploads/images/feature_image/93351bdf-d421-433c-ab8b-e6ea1a2cb768.jpeg"))
            }

        }
    }

    private fun setting() {
        binding.add.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, AddNewCardFragment())
                .addToBackStack("addNewCard")
                .commit()
        }
        binding.enter.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, Congratulations3Fragment())
                .addToBackStack("congratulation")
                .commit()
        }
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}


class PayMentMethodsViewModel(val context: Context): ViewModel(){
    val PaymentMethods = MutableLiveData(ArrayList<CardData>())
    val course = MutableLiveData<CoursesData>()
    private var selectedCard = CardData()
    val statePost = MutableLiveData<Int?>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun postData(bundle: Bundle?){
        if (bundle!=null){
            val id = bundle.getString("id", "")
            getData()
            getCourse(id)
        }
    }

    private fun getCourse(id: String?) {
        coroutineScope.launch {
            val responseData = GetCourseByIdRepository().request(id?:"")
            if (responseData?.response?.isSuccessful == true){
                val gson = Gson()
                val type = object: TypeToken<CoursesData>(){}.type
                course.postValue(gson.fromJson(responseData.body, type))
            }else{

            }
        }
    }

    fun getData(){
        PaymentMethods.value?.add(CardData(name = "PayPal"))
        PaymentMethods.value?.add(CardData(name = "Google Pay"))
        PaymentMethods.value?.add(CardData(name = "Apple Pay"))
        PaymentMethods.value?.add(CardData(name = "userCard", number = "2456 4321  6676  3054"))

        PaymentMethods.value = PaymentMethods.value
    }

    fun redactData(cardData: CardData) {
        selectedCard = cardData
    }
}

class PayMentMethodsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PayMentMethodsViewModel(context = context) as T
    }
}