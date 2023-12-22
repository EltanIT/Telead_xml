package com.example.telead_xml.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class IntroAdapter(behavior: Int, fm: FragmentManager):
    FragmentPagerAdapter(fm, behavior) {

    val list = ArrayList<Fragment>()

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    fun addFrag(fragment: Fragment){
        list.add(fragment)
    }
}