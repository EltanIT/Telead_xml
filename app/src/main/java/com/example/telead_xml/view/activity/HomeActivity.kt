package com.example.telead_xml.view.activity

import android.content.pm.ActivityInfo
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
import com.example.telead_xml.view.fragment.MyBookmarkFragment
import com.example.telead_xml.view.fragment.ProfilesFragment
import com.example.telead_xml.view.fragment.TransactionsFragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val homeFragment = HomeFragment()
    private val myCoursesFragment = MyBookmarkFragment()
    private val indoxFragment = IndoxFragment()
    private val transactionsFragment = TransactionsFragment()
    private val profilesFragment = ProfilesFragment()

    private var activeFragment: Fragment = homeFragment

    private var stateMyCoursesFragment = false
    private var stateIndoxFragment = false
    private var stateTransactionsFragment = false
    private var stateProfilesFragment = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment(activeFragment)
        selectFragment(activeFragment)
        setting()
    }


    private fun selectFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment).show(fragment).commit()
    }

    private fun addFragment(frag: Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.home_container_view, frag).hide(frag)
            .commit()
    }


    private fun setting() {
        binding.navBottom.setOnItemSelectedListener(object: OnItemSelectedListener,
            NavigationBarView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.home ->{
                        selectFragment(homeFragment)
                        activeFragment = homeFragment
                        return true
                    }
                    R.id.courses ->{
                        if (!stateMyCoursesFragment){
                            addFragment(myCoursesFragment)
                            stateMyCoursesFragment = true

                        }
                        selectFragment(myCoursesFragment)
                        activeFragment = myCoursesFragment

                        return true
                    }
                    R.id.indox ->{
                        if (!stateIndoxFragment){
                            addFragment(indoxFragment)
                            stateIndoxFragment = true

                        }
                        selectFragment(indoxFragment)
                        activeFragment = indoxFragment
                        return true
                    }
                    R.id.transaction ->{
                        if (!stateTransactionsFragment){
                            addFragment(transactionsFragment)
                            stateTransactionsFragment = true

                        }
                        selectFragment(transactionsFragment)
                        activeFragment = transactionsFragment
                        return true
                    }
                    R.id.profile ->{
                        if (!stateProfilesFragment){
                            addFragment(profilesFragment)
                            stateProfilesFragment = true

                        }
                        selectFragment(profilesFragment)
                        activeFragment = profilesFragment
                        return true
                    }
                }
                return false
            }

        })
    }
}