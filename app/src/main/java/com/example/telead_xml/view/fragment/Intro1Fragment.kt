package com.example.telead_xml.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.telead_xml.databinding.FragmentIntro1Binding
import com.example.telead_xml.view.activity.LoginActivity

class Intro1Fragment : Fragment() {

    private lateinit var binding: FragmentIntro1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentIntro1Binding.inflate(layoutInflater)
        setting()
        return binding.root
    }

    private fun setting() {
        binding.skip.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}
