package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullCoursesBinding
import com.example.telead_xml.databinding.ItemFullMyCompleteCoursesBinding
import com.example.telead_xml.databinding.ItemFullMyOngoingCoursesBinding
import com.example.telead_xml.databinding.ItemPopularCoursesHomeBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.domen.objects.OngoingCoursesData

class MyOngoingCoursesAdapter(val list: ArrayList<OngoingCoursesData>) : RecyclerView.Adapter<MyOngoingCoursesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFullMyOngoingCoursesBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_full_my_ongoing_courses, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courses = list[position]
        val completed = courses.completed
        val total = courses.total

        holder.binding.name.text = courses.name
        holder.binding.category.text = courses.category
        holder.binding.rating.text = courses.rating.toString()
        holder.binding.time.text = courses.time
        holder.binding.completed.text = completed.toString()
        holder.binding.total.text = total.toString()

        val completedPercent = (completed/total)*100
        holder.binding.progressBar.progress = completedPercent
    }

    override fun getItemCount(): Int {
        return list.size
    }

}