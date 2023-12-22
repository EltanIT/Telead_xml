package com.example.telead_xml.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.telead_xml.databinding.ActivityMainBinding
import com.example.telead_xml.view.adapter.IntroAdapter
import com.example.telead_xml.view.fragment.Intro1Fragment
import com.example.telead_xml.view.fragment.Intro2Fragment
import com.example.telead_xml.view.fragment.Intro3Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = IntroAdapter(FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, supportFragmentManager)
        adapter.addFrag(Intro1Fragment())
        adapter.addFrag(Intro2Fragment())
        adapter.addFrag(Intro3Fragment())

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}