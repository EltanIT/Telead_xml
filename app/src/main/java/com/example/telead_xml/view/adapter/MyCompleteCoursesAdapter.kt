package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullMyCompleteCoursesBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.listener.MyCourseListener
import java.lang.IndexOutOfBoundsException

class MyCompleteCoursesAdapter(val list: ArrayList<CoursesData>, val listener: MyCourseListener) : RecyclerView.Adapter<MyCompleteCoursesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFullMyCompleteCoursesBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_full_my_complete_courses, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courses = list[position]
        holder.binding.name.text = courses.name
        try{
            holder.binding.category.text = courses.benefits[0].name
        }catch (e: IndexOutOfBoundsException){

        }
        holder.binding.rating.text = courses.rating.toString()
        holder.binding.time.text = courses.durationInMinutes.toString() + "Мин"

        holder.binding.seeSertificate.setOnClickListener {
            listener.clickCertificate(courses.id!!)
        }

        holder.itemView.setOnClickListener {
            listener.click(courses.id)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}