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
import com.example.telead_xml.databinding.FragmentChatsBinding
import com.example.telead_xml.domen.objects.ChatData
import com.example.telead_xml.view.adapter.ChatAdapter

class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var vm: ChatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater)
        vm = ViewModelProvider(this, ChatsViewModelFactory(requireContext()))[ChatsViewModel::class.java]
        subscription()
        setting()
        vm.setting()
        return binding.root
    }

    private fun setting() {

    }

    private fun subscription() {
        vm.chatList.observe(viewLifecycleOwner){
            if (it!=null){
                binding.chatsRv.adapter = ChatAdapter(it)
            }
        }
    }

}


class ChatsViewModel(val context: Context): ViewModel(){
    val chatList = MutableLiveData(ArrayList<ChatData>())

    private fun getList(){
        chatList.value?.add(ChatData("Леша Держинский", "9:03", 5, "Привет, как дела?"))
        chatList.value?.add(ChatData("Петр М.", "12:18", 2, "Я сделал то, о чем ты просил"))
        chatList.value?.add(ChatData("Елена Карпова", "15:30", 0, "Вы все поняли?"))

        chatList.value = chatList.value
    }

    fun setting() {
        getList()
    }
}

class ChatsViewModelFactory(val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatsViewModel(context = context) as T
    }
}