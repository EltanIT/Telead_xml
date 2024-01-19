package com.example.telead_xml.view.activity

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.GONE
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentPagerAdapter
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ActivityMainBinding
import com.example.telead_xml.view.fragment.Intro1Fragment
import com.example.telead_xml.view.fragment.Intro2Fragment
import com.example.telead_xml.view.fragment.Intro3Fragment
import java.util.Timer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.Theme_Material3_DayNight_NoActionBar_Launching)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = com.example.telead_xml.view.adapter.FragmentAdapter(FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, supportFragmentManager)
        adapter.addFrag(Intro1Fragment())
        adapter.addFrag(Intro2Fragment())
        adapter.addFrag(Intro3Fragment())

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

//        binding.splashScreen.setOnClickListener {}
//
//        object: CountDownTimer(2000, 1000) {
//            override fun onTick(p0: Long) {
//
//            }
//
//            override fun onFinish() {
//                binding.splashScreen.visibility = GONE
//            }
//
//        }
    }

//    private fun startNotificationService(){
//        val intent = Intent(this, NotificationService::class.java)
//        startService(intent)
//    }
}