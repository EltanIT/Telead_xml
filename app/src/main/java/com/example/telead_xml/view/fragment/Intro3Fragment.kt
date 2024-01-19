package com.example.telead_xml.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentIntro2Binding
import com.example.telead_xml.databinding.FragmentIntro3Binding
import com.example.telead_xml.view.activity.HomeActivity
import com.example.telead_xml.view.activity.LoginActivity

class Intro3Fragment : Fragment() {

    private lateinit var binding: FragmentIntro3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntro3Binding.inflate(layoutInflater)
        setting()
        return binding.root
    }


    private fun setting() {
        binding.skip.setOnClickListener {
//            val intent = Intent(requireActivity(), LoginActivity::class.java)
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.start.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}