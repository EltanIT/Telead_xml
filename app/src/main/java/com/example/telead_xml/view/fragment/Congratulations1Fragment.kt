package com.example.telead_xml.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentCongratulations1Binding
import com.example.telead_xml.view.activity.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Congratulations1Fragment : Fragment() {

    private lateinit var binding: FragmentCongratulations1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratulations1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        setting()
        super.onResume()
    }

    private fun setting() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }


}