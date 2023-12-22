package com.example.telead_xml.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentLetsYouInBinding

class LetsYouInFragment : Fragment() {

    private lateinit var binding: FragmentLetsYouInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetsYouInBinding.inflate(layoutInflater)
        setting()
        return binding.root
    }

    private fun setting() {
        binding.login.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, LoginFragment())
                .addToBackStack("login")
                .commit()
        }
        binding.registartion.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, RegistrationNowFragment())
                .addToBackStack("registration")
                .commit()
        }
    }

}