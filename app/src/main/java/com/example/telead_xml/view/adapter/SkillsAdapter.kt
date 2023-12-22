package com.example.telead_xml.view.adapter

import android.annotation.SuppressLint
import android.provider.CallLog.Calls
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemCallBinding
import com.example.telead_xml.databinding.ItemFullCoursesBinding
import com.example.telead_xml.databinding.ItemFullMyCompleteCoursesBinding
import com.example.telead_xml.databinding.ItemFullMyOngoingCoursesBinding
import com.example.telead_xml.databinding.ItemLessonBinding
import com.example.telead_xml.databinding.ItemPopularCoursesHomeBinding
import com.example.telead_xml.databinding.ItemSkillBinding
import com.example.telead_xml.domen.objects.CallData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.LessonData
import com.example.telead_xml.domen.objects.OngoingCoursesData
import com.example.telead_xml.domen.objects.SkillData

class SkillsAdapter(val list: ArrayList<SkillData>) : RecyclerView.Adapter<SkillsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSkillBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val call = list[position]

        holder.binding.name.text = call.name

    }

    override fun getItemCount(): Int {
        return list.size
    }

}