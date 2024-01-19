package com.example.telead_xml.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.telead_xml.R
import com.example.telead_xml.databinding.FragmentCongratulation4Binding

class Congratulations4Fragment : Fragment() {

    private lateinit var binding: FragmentCongratulation4Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratulation4Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        setting()
        super.onResume()
    }

    private fun setting() {
        binding.receipt.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.full_home_container_view, EReceiptFragment())
                .addToBackStack("eReceipt")
                .commit()
        }
    }
}