package com.example.telead_xml.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ActivityHomeBinding
import com.example.telead_xml.view.fragment.HomeFragment
import com.example.telead_xml.view.fragment.IndoxFragment
import com.example.telead_xml.view.fragment.MyCoursesFragment
import com.example.telead_xml.view.fragment.ProfilesFragment
import com.example.telead_xml.view.fragment.TransactionsFragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val homeFragment = HomeFragment()
    private val myCoursesFragment = MyCoursesFragment()
    private val indoxFragment = IndoxFragment()
    private val transactionsFragment = TransactionsFragment()
    private val profilesFragment = ProfilesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeFragment)
        setting()
    }

    private fun replaceFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_container_view, frag)
            .commit()
    }


    private fun setting() {
        binding.navBottom.setOnItemSelectedListener(object: OnItemSelectedListener,
            NavigationBarView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.home ->{
                        replaceFragment(homeFragment)
                        return true
                    }
                    R.id.courses ->{
                        replaceFragment(myCoursesFragment)
                        return true
                    }
                    R.id.indox ->{
                        replaceFragment(indoxFragment)
                        return true
                    }
                    R.id.transaction ->{
                        replaceFragment(transactionsFragment)
                        return true
                    }
                    R.id.profile ->{
                        replaceFragment(profilesFragment)
                        return true
                    }
                }
                return false
            }

        })
    }
}